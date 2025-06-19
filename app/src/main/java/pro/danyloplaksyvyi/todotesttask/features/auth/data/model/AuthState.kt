package pro.danyloplaksyvyi.todotesttask.features.auth.data.model

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class SignedIn(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}