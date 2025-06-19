package pro.danyloplaksyvyi.todotesttask

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import pro.danyloplaksyvyi.todotesttask.features.auth.data.repository.AuthRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.AuthRepository
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignInUseCase
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignUpUseCase
import pro.danyloplaksyvyi.todotesttask.features.auth.presentation.viewmodel.AuthViewModel
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.api.TaskApiService
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.repository.TaskRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.TaskRepository
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.GetTasksUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.presentation.viewmodel.TaskViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

val taskModule = module {
    // Retrofit Builder
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Api Service
    single<TaskApiService> { get<Retrofit>().create(TaskApiService::class.java) }
    // Repository
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    // Use Case
    factory { GetTasksUseCase(get()) }
    // ViewModel
    viewModelOf(::TaskViewModel)
}

// Combine all modules
val appModules = listOf(authModule, taskModule)