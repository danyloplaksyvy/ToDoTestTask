package pro.danyloplaksyvyi.todotesttask.features.auth.data.repository

import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.User


interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<User>
    fun signOut()
    fun getCurrentUser(): User?
}