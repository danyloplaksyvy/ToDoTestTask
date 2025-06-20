package pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository

interface AnalyticsRepository {
    fun logTaskOpened(taskId: Int, taskTitle: String)
    fun logFirstOpen()
    fun logCacheClear()
}