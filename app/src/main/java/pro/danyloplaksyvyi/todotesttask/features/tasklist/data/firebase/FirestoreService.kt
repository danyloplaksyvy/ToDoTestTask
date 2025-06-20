package pro.danyloplaksyvyi.todotesttask.features.tasklist.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.StoredTask

class FirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    private val tasksCollection = firestore.collection("stored_tasks")

    suspend fun saveTask(task: StoredTask): Result<Unit> {
        return try {
            tasksCollection.add(task).await()

            // Keep only last 5 tasks - delete older ones
            val allTasks = tasksCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            if (allTasks.documents.size > 5) {
                val tasksToDelete = allTasks.documents.drop(5)
                tasksToDelete.forEach { document ->
                    document.reference.delete().await()
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLastTasks(limit: Int): Result<List<StoredTask>> {
        return try {
            val snapshot = tasksCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()

            val tasks = snapshot.documents.mapNotNull { document ->
                document.toObject(StoredTask::class.java)?.copy(id = document.id)
            }

            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun clearAllTasks(): Result<Unit> {
        return try {
            val snapshot = tasksCollection.get().await()
            snapshot.documents.forEach { document ->
                document.reference.delete().await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}