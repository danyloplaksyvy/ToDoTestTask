package pro.danyloplaksyvyi.todotesttask.features.tasklist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.Task
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.GetTasksUseCase

class TaskViewModel(private val getTasksUseCase: GetTasksUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(DataDownloadUiState())
    val uiState: StateFlow<DataDownloadUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            getTasksUseCase().fold(
                onSuccess = { tasks ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        tasks = tasks,
                        filteredTasks = filterTasks(tasks, _uiState.value.selectedFilter)
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }

    fun onFilterChanged(filter: TaskFilter) {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            selectedFilter = filter,
            filteredTasks = filterTasks(currentState.tasks, filter)
        )
    }

    private fun filterTasks(tasks: List<Task>, filter: TaskFilter): List<Task> {
        return when (filter) {
            TaskFilter.ALL -> tasks
            TaskFilter.COMPLETED -> tasks.filter { it.completed }
            TaskFilter.INCOMPLETE -> tasks.filter { !it.completed }
        }
    }

    fun retry() {
        loadTasks()
    }
}


data class DataDownloadUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val filteredTasks: List<Task> = emptyList(),
    val selectedFilter: TaskFilter = TaskFilter.ALL,
    val error: String? = null
)

enum class TaskFilter(val displayName: String) {
    ALL("All Tasks"),
    COMPLETED("Completed"),
    INCOMPLETE("Incomplete")
}