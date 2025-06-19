package pro.danyloplaksyvyi.todotesttask.features.tasklist.data.api

import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.dto.TaskDto
import retrofit2.http.GET

interface TaskApiService {
    @GET("todos")
    suspend fun getTasks(): List<TaskDto>
}