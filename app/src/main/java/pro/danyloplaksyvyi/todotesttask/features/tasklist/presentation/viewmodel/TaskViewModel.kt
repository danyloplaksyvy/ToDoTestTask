package pro.danyloplaksyvyi.todotesttask.features.tasklist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.StoredTask
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.model.Task
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.repository.AnalyticsRepository
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.ClearCacheUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.GetStoredTasksUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.GetTasksUseCase
import pro.danyloplaksyvyi.todotesttask.features.tasklist.domain.usecase.SaveTaskUseCase

class TaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getStoredTasksUseCase: GetStoredTasksUseCase,
    private val clearCacheUseCase: ClearCacheUseCase,
    private val analyticsRepository: AnalyticsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private var isFirstOpen = true

    init {
        if (isFirstOpen) {
            analyticsRepository.logFirstOpen()
            isFirstOpen = false
        }
        loadTasks()
        loadStoredTasks()
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

    fun loadStoredTasks() {
        viewModelScope.launch {
            getStoredTasksUseCase().fold(
                onSuccess = { storedTasks ->
                    _uiState.value = _uiState.value.copy(storedTasks = storedTasks)
                },
                onFailure = { /* Handle error silently for stored tasks */ }
            )
        }
    }

    fun onTaskClicked(task: Task) {
        analyticsRepository.logTaskOpened(task.id, task.title)

        // Save task to Firestore
        viewModelScope.launch {
            saveTaskUseCase(task).fold(
                onSuccess = { loadStoredTasks() }, // Refresh stored tasks
                onFailure = { /* Handle error silently */ }
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

    fun clearCache() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isClearingCache = true)

            clearCacheUseCase().fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isClearingCache = false,
                        storedTasks = emptyList(),
                        cacheMessage = "Cache cleared successfully!"
                    )
                    // Auto-clear after delay
                    delay(3000) // Show for 3 seconds
                    _uiState.value = _uiState.value.copy(cacheMessage = null)
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isClearingCache = false,
                        cacheMessage = "Failed to clear cache: ${exception.message}"
                    )
                    // Auto-clear after delay
                    delay(3000) // Show for 3 seconds
                    _uiState.value = _uiState.value.copy(cacheMessage = null)
                }
            )
        }
    }

    fun dismissCacheMessage() {
        _uiState.value = _uiState.value.copy(cacheMessage = null)
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

data class TaskUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val filteredTasks: List<Task> = emptyList(),
    val storedTasks: List<StoredTask> = emptyList(),
    val selectedFilter: TaskFilter = TaskFilter.ALL,
    val error: String? = null,
    val isClearingCache: Boolean = false,
    val cacheMessage: String? = null
)

enum class TaskFilter(val displayName: String) {
    ALL("All Tasks"),
    COMPLETED("Completed"),
    INCOMPLETE("Incomplete")
}