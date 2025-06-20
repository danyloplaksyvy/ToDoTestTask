package pro.danyloplaksyvyi.todotesttask.features.tasklist.data.repository

import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.api.TaskApiService
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.dto.toTask
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.Task
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskApiService: TaskApiService
) : TaskRepository {
    override suspend fun getTasks(): Result<List<Task>> {
        return try {
            val tasksDto = taskApiService.getTasks()
            val tasks = tasksDto.map { it.toTask() }
            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}