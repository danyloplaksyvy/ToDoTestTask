package pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.screen

sealed class AuthScreens(val route: String) {
    object SignIn: AuthScreens("sign_in")
    object SignUp: AuthScreens("sign_up")
}