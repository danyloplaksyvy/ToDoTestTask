package pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.danyloplaksyvyi.todotesttask.features.auth.data.model.User


interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<User>
    fun signOut()
    fun getCurrentUser(): Flow<User?>
}