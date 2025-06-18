package pro.danyloplaksyvyi.todotesttask.core.navigation.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pro.danyloplaksyvyi.todotesttask.features.auth.presentation.view.SignInScreen
import pro.danyloplaksyvyi.todotesttask.features.auth.presentation.view.SignUpScreen
import pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.Graph
import pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.screen.AuthScreens

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreens.SignIn.route
    ) {
        composable(AuthScreens.SignIn.route) {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate(Graph.MAIN) {
                        // Pop everything up to and including AUTH graph
                        popUpTo(Graph.AUTH) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSignUpNavigate = {
                    navController.navigate(AuthScreens.SignUp.route)
                }
            )
        }
        composable(AuthScreens.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Graph.MAIN) {
                        // Clear entire auth stack
                        popUpTo(Graph.AUTH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
