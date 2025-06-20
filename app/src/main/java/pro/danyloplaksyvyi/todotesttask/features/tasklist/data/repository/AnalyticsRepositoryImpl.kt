package pro.danyloplaksyvyi.todotesttask.features.tasklist.data.repository

import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.firebase.AnalyticsService
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.AnalyticsRepository

class AnalyticsRepositoryImpl(
    private val analyticsService: AnalyticsService
) : AnalyticsRepository {

    override fun logTaskOpened(taskId: Int, taskTitle: String) {
        analyticsService.logTaskOpened(taskId, taskTitle)
    }

    override fun logFirstOpen() {
        analyticsService.logFirstOpen()
    }

    override fun logCacheClear() {
        analyticsService.logCacheClear()
    }
}