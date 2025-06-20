package pro.danyloplaksyvyi.todotesttask.features.tasklist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.api.TaskApiService
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.firebase.AnalyticsService
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.firebase.FirestoreService
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.repository.AnalyticsRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.repository.StoredTasksRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.tasklist.data.repository.TaskRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.AnalyticsRepository
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.StoredTasksRepository
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.TaskRepository
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.ClearCacheUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.GetStoredTasksUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.GetTasksUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.SaveTaskUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.presentation.viewmodel.TaskViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val taskModule = module {

    // Services
    single { FirestoreService() }
    single { AnalyticsService(get()) }

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
    single<StoredTasksRepository> { StoredTasksRepositoryImpl(get()) }
    single<AnalyticsRepository> { AnalyticsRepositoryImpl(get()) }
    // Use Case
    factory { GetTasksUseCase(get()) }
    factory { ClearCacheUseCase(get(), get()) }
    factory { GetStoredTasksUseCase(get()) }
    factory { SaveTaskUseCase(get()) }
    // ViewModel
    viewModel { TaskViewModel(get(), get(), get(), get(), get()) }
}
