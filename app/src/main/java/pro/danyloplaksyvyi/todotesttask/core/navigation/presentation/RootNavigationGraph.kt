package pro.danyloplaksyvyi.todotesttask.core.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.Graph
import pro.danyloplaksyvyi.todotesttask.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun RootNavigationGraph() {
    val rootNavController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val currentUser by authViewModel.currentUser.collectAsState()

    // Determine start destination based on auth state
    val startDestination = if (currentUser != null) Graph.MAIN else Graph.AUTH

    NavHost(navController = rootNavController, startDestination = startDestination) {
        authNavGraph(rootNavController, authViewModel = authViewModel)
        mainNavGraph(rootNavController)
    }
}