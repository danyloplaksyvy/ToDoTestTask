package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model

import java.util.Date

data class StoredTask(
    val id: String = "",
    val userId: Int = 0,
    val taskId: Int = 0,
    val title: String = "",
    val completed: Boolean = false,
    val timestamp: Date = Date()
)

fun Task.toStoredTask(): StoredTask {
    return StoredTask(
        id = "", // Firestore will generate this
        userId = userId,
        taskId = id,
        title = title,
        completed = completed,
        timestamp = Date()
    )
}
