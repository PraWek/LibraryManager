package com.example.librarymanager

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import com.example.library.LibraryItem
import com.example.library.LibraryManager

class LibraryViewModel : ViewModel() {
    private val libraryManager = LibraryManager()

    private var _listScrollPosition = 0
    var listScrollPosition: Int
        get() = _listScrollPosition
        set(value) {
            _listScrollPosition = value
        }

    var items by mutableStateOf(libraryManager.getAllItems())
        private set

    var selectedItem by mutableStateOf<LibraryItem?>(null)
        private set

    var isDetailVisible by mutableStateOf(false)
        private set

    var showAddItemDialog by mutableStateOf(false)
        private set

    var showTypeDialog by mutableStateOf(false)
        private set

    var selectedType by mutableStateOf<String?>(null)
        private set

    var wasDetailVisibleInPortrait by mutableStateOf(false)
        private set

    fun toggleTypeDialog(show: Boolean) {
        showTypeDialog = show
    }

    fun updateSelectedType(type: String?) {
        selectedType = type
    }

    fun showDetailInRight(item: LibraryItem) {
        selectedItem = item
        isDetailVisible = true
        wasDetailVisibleInPortrait = false
    }

    fun navigateToDetail(item: LibraryItem) {
        selectedItem = item
        isDetailVisible = true
        wasDetailVisibleInPortrait = true
    }

    fun handleOrientationChange(isLandscape: Boolean) {
        if (isLandscape && wasDetailVisibleInPortrait) {
            // Keep detail visible when rotating to landscape
            isDetailVisible = true
        } else if (!isLandscape && !wasDetailVisibleInPortrait) {
            // Hide detail when rotating to portrait if it wasn't opened in portrait
            isDetailVisible = false
        }
    }

    fun onAddItemClick() {
        selectedItem = null
        isDetailVisible = true
        showAddItemDialog = true
    }

    fun onBackPressed(): Boolean {
        return when {
            isDetailVisible -> {
                isDetailVisible = false
                selectedItem = null
                showAddItemDialog = false
                true
            }
            else -> false
        }
    }

    fun addItem(newItem: LibraryItem) {
        libraryManager.addItem(newItem)
        items = libraryManager.getAllItems()
        showAddItemDialog = false
        isDetailVisible = false
    }
}