package pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase

import pro.danyloplaksyvyi.todotesttask.features.auth.domain.model.User
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.AuthRepository

class SignInUseCase(
    val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.signInWithEmailAndPassword(email, password)
    }
}