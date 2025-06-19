package pro.danyloplaksyvyi.todotesttask.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.model.User
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignInUseCase
import pro.danyloplaksyvyi.todotesttask.features.auth.domain.usecase.SignUpUseCase

class AuthViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpFormState = MutableStateFlow(SignUpFormState())
    val signUpFormState: StateFlow<SignUpFormState> = _signUpFormState.asStateFlow()

    private val _signInFormState = MutableStateFlow(SignInFormState())
    val signInFormState: StateFlow<SignInFormState> = _signInFormState.asStateFlow()

    // Expose current user state from repository
    val currentUser: StateFlow<User?> = signInUseCase.authRepository.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

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
        validateSignUpForm()
    }

    fun updateSignUpEmail(email: String) {
        val error = if (email.isNotEmpty() && !(email.contains("@") && email.contains("."))) {
            "Please enter a valid email address"
        } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            email = email,
            emailError = error
        )
        validateSignUpForm()
    }

    fun updateSignInEmail(email: String) {
        val error = if (email.isNotEmpty() && !(email.contains("@") && email.contains("."))) {
            "Please enter a valid email address"
        } else null
        _signInFormState.value = _signInFormState.value.copy(
            email = email,
            emailError = error
        )
        validateSignInForm()
    }

    fun updateSignUpPassword(password: String) {
        val error = if (password.isNotEmpty() && password.length < 6) {
            "Password must be at least 6 characters"
        } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            password = password,
            passwordError = error
        )
        validateSignUpForm()
    }

    fun updateSignInPassword(password: String) {
        val error = if (password.isNotEmpty() && password.length < 6) {
            "Password must be at least 6 characters"
        } else null
        _signInFormState.value = _signInFormState.value.copy(
            password = password,
            passwordError = error
        )
        validateSignInForm()
    }

    fun updateConfirmPassword(confirmPassword: String) {
        val error =
            if (confirmPassword.isNotEmpty() && confirmPassword != _signUpFormState.value.password) {
                "Passwords do not match"
            } else null
        _signUpFormState.value = _signUpFormState.value.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = error
        )
        validateSignUpForm()
    }

    private fun validateSignUpForm() {
        val state = _signUpFormState.value
        val isValid = state.fullNameError == null &&
                state.emailError == null &&
                state.passwordError == null &&
                state.confirmPasswordError == null &&
                state.fullName.isNotEmpty() &&
                state.email.isNotEmpty() &&
                state.password.isNotEmpty() &&
                state.confirmPassword.isNotEmpty()
        _signUpFormState.value = state.copy(isFormValid = isValid)
    }

    private fun validateSignInForm() {
        val state = _signInFormState.value
        val isValid = state.emailError == null &&
                state.passwordError == null &&
                state.email.isNotEmpty() &&
                state.password.isNotEmpty()
        _signInFormState.value = state.copy(isFormValid = isValid)
    }

    fun signUp() {
        val state = _signUpFormState.value
        if (state.isFormValid) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                val result = signUpUseCase(state.email, state.password)
                _authState.value = when {
                    result.isSuccess -> AuthState.SignedIn(
                        result.getOrNull() ?: User(
                            "dummy_id",
                            "myemail@gmail.com"
                        )
                    )

                    else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Sign up failed")
                }
            }
        }
    }

    fun signIn() {
        val state = _signInFormState.value
        if (state.isFormValid) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                val result = signInUseCase(state.email, state.password)
                _authState.value = when {
                    result.isSuccess -> AuthState.SignedIn(
                        result.getOrNull() ?: User(
                            "dummy_id",
                            "myemail@gmail.com"
                        )
                    )

                    else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Sign in failed")
                }
            }
        }
    }


}