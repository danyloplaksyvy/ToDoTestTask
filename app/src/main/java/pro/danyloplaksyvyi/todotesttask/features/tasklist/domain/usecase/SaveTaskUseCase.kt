package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase

import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.Task
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.toStoredTask
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.StoredTasksRepository

class SaveTaskUseCase(
    private val storedTasksRepository: StoredTasksRepository
) {
    suspend operator fun invoke(task: Task): Result<Unit> {
        return storedTasksRepository.saveTask(task.toStoredTask())
    }
}