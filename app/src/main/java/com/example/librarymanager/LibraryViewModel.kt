package com.example.librarymanager

import androidx.lifecycle.*
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import com.example.library.LibraryItem
import com.example.library.LibraryManager
import kotlin.random.Random

enum class SortType {
    BY_NAME,
    BY_DATE
}

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<LibraryItem> = emptyList(),
    val sortType: SortType = SortType.BY_DATE,
    val hasMoreItems: Boolean = false
)

data class ScrollPosition(val index: Int, val offset: Int)

class LibraryViewModel : ViewModel() {
    private val libraryManager = LibraryManager()
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState> = _uiState
    private var errorCounter = 0
    private var currentPage = 0
    private val pageSize = 30 // Approximately 10 items visible * 3
    private lateinit var dataStore: DataStore

    fun initDataStore(context: android.content.Context) {
        dataStore = DataStore(context)
    }

    init {
        viewModelScope.launch {
            val savedSortType = dataStore.getSortType.first()
            _uiState.value = _uiState.value.copy(sortType = savedSortType)
            loadItems(refresh = true)
        }
    }

    fun setSortType(sortType: SortType) {
        viewModelScope.launch {
            dataStore.saveSortType(sortType)
            _uiState.value = _uiState.value.copy(sortType = sortType)
            loadItems(refresh = true)
        }
    }

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

    fun loadItems(refresh: Boolean = false, loadPrevious: Boolean = false) {
        viewModelScope.launch {
            try {
                if (refresh) {
                    currentPage = 0
                    _uiState.value = _uiState.value.copy(isLoading = true, items = emptyList())
                }

                val minLoadingDelay = async { delay(1000) }

                val targetPage = if (loadPrevious) currentPage - 1 else currentPage + 1
                if (targetPage < 0) return@launch

                _uiState.value = _uiState.value.copy(isLoading = true)

                val newItems = withContext(Dispatchers.IO) {
                    delay(Random.nextLong(100, 2000))

                    errorCounter++
                    if (errorCounter % 3 == 0) {
                        throw Exception("Failed to load items from database")
                    }

                    libraryManager.getItems(
                        page = if (refresh) 0 else targetPage,
                        pageSize = pageSize,
                        sortType = _uiState.value.sortType
                    )
                }

                minLoadingDelay.await()

                val currentItems = if (refresh) emptyList() else _uiState.value.items
                val updatedItems = when {
                    refresh -> newItems
                    loadPrevious -> {
                        currentPage = targetPage
                        newItems + currentItems.dropLast(minOf(pageSize / 2, newItems.size))
                    }
                    else -> {
                        currentPage = targetPage
                        currentItems.dropFirst(minOf(pageSize / 2, newItems.size)) + newItems
                    }
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    items = updatedItems,
                    hasMoreItems = newItems.size == pageSize,
                    error = null
                )

                currentPage++
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun loadMoreItems() {
        if (!_uiState.value.isLoading && _uiState.value.hasMoreItems) {
            loadItems(loadPrevious = false)
        }
    }

    fun loadPreviousItems() {
        if (!_uiState.value.isLoading && currentPage > 0) {
            loadItems(loadPrevious = true)
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
