package pro.danyloplaksyvyi.todotesttask.features.auth.presentation.viewmodel

import pro.danyloplaksyvyi.todotesttask.features.auth.domain.model.User

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class SignedIn(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}