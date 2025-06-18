package pro.danyloplaksyvyi.todotesttask.core.navigation.data.model.screen

sealed class MainScreens(val route: String) {
    object TaskList: MainScreens("task_list")

}