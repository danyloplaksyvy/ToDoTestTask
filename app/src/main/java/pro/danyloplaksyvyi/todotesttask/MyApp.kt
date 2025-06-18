package pro.danyloplaksyvyi.todotesttask

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import pro.danyloplaksyvyi.todotesttask.features.auth.data.repository.AuthRepository
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.AuthRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.auth.presentation.viewmodel.AuthViewModel

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }
    }
}

// Repository module
val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
}

// ViewModel module
val viewModelModule = module {
    viewModelOf(::AuthViewModel)
}

// Combine all modules
val appModules = listOf(repositoryModule, viewModelModule)