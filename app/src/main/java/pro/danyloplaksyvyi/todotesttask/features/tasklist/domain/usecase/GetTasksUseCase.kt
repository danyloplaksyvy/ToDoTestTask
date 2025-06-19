package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase

import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.Task
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Result<List<Task>> {
        return repository.getTasks()
    }
}
