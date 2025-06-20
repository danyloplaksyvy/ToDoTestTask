package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository

import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): Result<List<Task>>
}