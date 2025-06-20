package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase

import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.StoredTask
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.StoredTasksRepository

class GetStoredTasksUseCase(private val storedTasksRepository: StoredTasksRepository) {
    suspend operator fun invoke(): Result<List<StoredTask>> {
        return storedTasksRepository.getLastTasks(5)
    }
}