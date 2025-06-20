package pro.danyloplaksyvyi.todotesttask.features.auth.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import pro.danyloplaksyvyi.todotesttask.features.auth.data.repository.AuthRepositoryImpl
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.AuthRepository
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignInUseCase
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignUpUseCase
import pro.danyloplaksyvyi.todotesttask.features.auth.presentation.viewmodel.AuthViewModel

val authModule = module {
    // Provide AuthRepository
    single<AuthRepository> { AuthRepositoryImpl() }

    // Provide Use Cases
    factory { SignInUseCase(get()) }
    factory { SignUpUseCase(get()) }

    // Provide ViewModel
    viewModelOf(::AuthViewModel)
}