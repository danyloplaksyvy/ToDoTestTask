package pro.danyloplaksyvyi.todotesttask.features.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import pro.danyloplaksyvyi.todotesttask.features.auth.data.model.User
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                val user = User(uid = firebaseUser.uid, email = firebaseUser.email ?: "")
                Result.success(user)
            } else {
                Result.failure(Exception("Sign in failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                val user = User(uid = firebaseUser.uid, email = firebaseUser.email ?: "")
                Result.success(user)
            } else {
                Result.failure(Exception("Sign up failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let { User(uid = it.uid, email = it.email ?: "") }
    }
}