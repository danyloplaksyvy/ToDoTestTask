package pro.danyloplaksyvyi.todotesttask.features.tasklist.data.dto

import com.google.gson.annotations.SerializedName
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.Task

data class TaskDto(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("completed")
    val completed: Boolean
)

fun TaskDto.toTask(): Task {
    return Task(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
}
