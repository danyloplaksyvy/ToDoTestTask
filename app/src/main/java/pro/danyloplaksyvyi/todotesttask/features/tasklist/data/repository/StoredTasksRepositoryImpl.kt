package pro.danyloplaksyvyi.todotesttask.features.tasklist.data.repository

import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.firebase.FirestoreService
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.StoredTask
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.StoredTasksRepository

class StoredTasksRepositoryImpl(private val firestoreService: FirestoreService): StoredTasksRepository {
    override suspend fun saveTask(task: StoredTask): Result<Unit> {
        return firestoreService.saveTask(task)
    }

    override suspend fun getLastTasks(limit: Int): Result<List<StoredTask>> {
        return firestoreService.getLastTasks(limit)
    }

    override suspend fun clearAllTasks(): Result<Unit> {
        return firestoreService.clearAllTasks()
    }

}