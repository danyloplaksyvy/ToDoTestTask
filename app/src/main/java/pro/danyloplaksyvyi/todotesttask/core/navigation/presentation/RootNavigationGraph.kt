package pro.danyloplaksyvyi.todotesttask.core.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.Graph

@Composable
fun RootNavigationGraph() {
    val rootNavController = rememberNavController()
    NavHost(navController = rootNavController, startDestination = Graph.AUTH) {
        authNavGraph(rootNavController)
        mainNavGraph(rootNavController)
    }
}