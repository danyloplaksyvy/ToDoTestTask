package pro.danyloplaksyvyi.todotesttask

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.AuthRepository
import pro.danyloplaksyvyi.todotesttask.features.auth.data.repository.AuthRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignInUseCase
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignUpUseCase
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

val authModule = module {
    // Provide AuthRepository
    single<AuthRepository> { AuthRepositoryImpl() }

    // Provide Use Cases
    factory { SignInUseCase(get()) }
    factory { SignUpUseCase(get()) }

    // Provide ViewModel
    viewModelOf(::AuthViewModel)
}

// Combine all modules
val appModules = listOf(authModule)