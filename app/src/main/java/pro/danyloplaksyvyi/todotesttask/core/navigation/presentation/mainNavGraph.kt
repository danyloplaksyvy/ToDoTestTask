package pro.danyloplaksyvyi.todotesttask.core.navigation.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.Graph
import pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.screen.MainScreens
import pro.danyloplaksyvyi.todotesttask.features.tasklist.presentation.TaskListScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(route = Graph.MAIN, startDestination = MainScreens.TaskList.route) {
        composable(MainScreens.TaskList.route) {
            TaskListScreen()
        }
    }
}