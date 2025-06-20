package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase

import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.AnalyticsRepository
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.StoredTasksRepository

class ClearCacheUseCase(
    private val localStorageRepository: StoredTasksRepository,
    private val analyticsRepository: AnalyticsRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        analyticsRepository.logCacheClear()
        return localStorageRepository.clearAllTasks()
    }
}
