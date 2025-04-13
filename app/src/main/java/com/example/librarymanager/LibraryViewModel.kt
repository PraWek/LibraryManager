package com.example.librarymanager

import androidx.lifecycle.*
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import com.example.library.LibraryItem
import com.example.library.LibraryManager
import kotlin.random.Random

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<LibraryItem> = emptyList()
)

data class ScrollPosition(val index: Int, val offset: Int)

class LibraryViewModel : ViewModel() {
    private val libraryManager = LibraryManager()
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState> = _uiState
    private var errorCounter = 0

    var selectedItem by mutableStateOf<LibraryItem?>(null)
        private set

    var isDetailVisible by mutableStateOf(false)
        private set

    var showTypeDialog by mutableStateOf(false)
        private set

    var selectedType by mutableStateOf<String?>(null)
        private set

    var scrollPosition by mutableStateOf<ScrollPosition?>(null)
        private set

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val minLoadingDelay = async { delay(1000) }

                val items = withContext(Dispatchers.IO) {
                    delay(Random.nextLong(100, 2000))

                    errorCounter++
                    if (errorCounter % 3 == 0) {
                        throw Exception("Failed to load items from database")
                    }

                    libraryManager.getAllItems()
                }

                minLoadingDelay.await()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    items = items,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun addItem(item: LibraryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(Random.nextLong(100, 2000)) // Simulate DB write
                libraryManager.addItem(item)
                loadItems()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to add item: ${e.message}"
                )
            }
        }
    }

    fun setScrollPosition(index: Int, offset: Int) {
        scrollPosition = ScrollPosition(index, offset)
    }

    fun navigateToDetail(item: LibraryItem) {
        selectedItem = item
        isDetailVisible = true
    }

    fun toggleTypeDialog(show: Boolean) {
        showTypeDialog = show
    }

    fun updateSelectedType(type: String?) {
        selectedType = type
    }

    fun showDetailInRight(item: LibraryItem) {
        selectedItem = item
    }

    fun selectItem(item: LibraryItem?) {
        selectedItem = item
    }

    fun handleBack(): Boolean {
        return if (isDetailVisible) {
            isDetailVisible = false
            true
        } else {
            false
        }
    }
}
