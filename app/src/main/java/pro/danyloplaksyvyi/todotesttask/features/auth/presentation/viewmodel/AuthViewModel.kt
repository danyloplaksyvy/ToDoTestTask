package pro.danyloplaksyvyi.todotesttask.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pro.danyloplaksyvyi.todotesttask.features.auth.data.repository.AuthRepository
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.repository.User

import kotlinx.coroutines.flow.asStateFlow

data class SignUpFormState(
    val fullName: String = "",
    val fullNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val acceptTerms: Boolean = false,
    val acceptTermsError: String? = null,
    val isFormValid: Boolean = false
)

class AuthViewModel(
    private val authRepository: AuthRepository // Injected via Koin, handles auth operations
) : ViewModel() {

    private val _signUpFormState = MutableStateFlow(SignUpFormState())
    val signUpFormState: StateFlow<SignUpFormState> = _signUpFormState.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun updateFullName(fullName: String) {
        val error = if (fullName.isNotEmpty() && fullName.trim().length < 2) {
            "Name must be at least 2 characters"
        } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            fullName = fullName,
            fullNameError = error
        )
        validateForm()
    }

    fun updateEmail(email: String) {
        val error = if (email.isNotEmpty() && !(email.contains("@") && email.contains("."))) {
            "Please enter a valid email address"
        } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            email = email,
            emailError = error
        )
        validateForm()
    }

    fun updatePassword(password: String) {
        val error = if (password.isNotEmpty() && password.length < 6) {
            "Password must be at least 6 characters"
        } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            password = password,
            passwordError = error
        )
        validateForm()
    }

    fun updateConfirmPassword(confirmPassword: String) {
        val error = if (confirmPassword.isNotEmpty() && confirmPassword != _signUpFormState.value.password) {
            "Passwords do not match"
        } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = error
        )
        validateForm()
    }

    fun updateAcceptTerms(accept: Boolean) {
        val error = if (!accept && _signUpFormState.value.acceptTerms) {
            "Please accept the terms and conditions"
        } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            acceptTerms = accept,
            acceptTermsError = error
        )
        validateForm()
    }

    private fun validateForm() {
        val state = _signUpFormState.value
        val isValid = state.fullNameError == null &&
                state.emailError == null &&
                state.passwordError == null &&
                state.confirmPasswordError == null &&
                state.acceptTerms &&
                state.fullName.isNotEmpty() &&
                state.email.isNotEmpty() &&
                state.password.isNotEmpty() &&
                state.confirmPassword.isNotEmpty()
        _signUpFormState.value = state.copy(isFormValid = isValid)
    }

    fun signUp() {
        val state = _signUpFormState.value
        if (state.isFormValid) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                // Simulate or replace with actual repository call
                val result = authRepository.signUpWithEmailAndPassword(state.email, state.password)
                _authState.value = when {
                    result.isSuccess -> AuthState.SignedIn(result.getOrNull() ?: User("dummy_id", "my_email@gmail.com"))
                    else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Sign up failed")
                }
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class SignedIn(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}