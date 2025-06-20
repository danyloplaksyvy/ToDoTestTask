package pro.danyloplaksyvyi.todotesttask

import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import pro.danyloplaksyvyi.todotesttask.features.auth.di.authModule
import pro.danyloplaksyvyi.todotesttask.features.tasklist.di.taskModule

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }
    }
}

// Combine all modules
val appModules = listOf(authModule, taskModule)