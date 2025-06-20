package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository

import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.StoredTask

interface StoredTasksRepository {
    suspend fun saveTask(task: StoredTask): Result<Unit>
    suspend fun getLastTasks(limit: Int = 5): Result<List<StoredTask>>
    suspend fun clearAllTasks(): Result<Unit>
}
