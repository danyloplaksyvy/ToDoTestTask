package pro.danyloplaksyvyi.todotesttask.features.auth.data.model

data class SignUpFormState(
    val fullName: String = "",
    val fullNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isFormValid: Boolean = false
)
