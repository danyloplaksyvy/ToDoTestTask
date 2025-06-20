package pro.danyloplaksyvyi.todotesttask.features.tasklist.data.firebase

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

@Suppress("DEPRECATION")
class AnalyticsService(context: Context) {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logTaskOpened(taskId: Int, taskTitle: String) {
        firebaseAnalytics.logEvent("task_opened") {
            param("task_id", taskId.toLong())
            param("task_title", taskTitle)
            param("timestamp", System.currentTimeMillis())
        }
    }

    fun logFirstOpen() {
        firebaseAnalytics.logEvent("first_open") {
            param("timestamp", System.currentTimeMillis())
        }
    }

    fun logCacheClear() {
        firebaseAnalytics.logEvent("cache_cleared") {
            param("timestamp", System.currentTimeMillis())
        }
    }
}
