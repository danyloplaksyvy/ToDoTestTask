package pro.danyloplaksyvyi.todotesttask.features.auth.data.model

data class SignInFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isFormValid: Boolean = false
)
