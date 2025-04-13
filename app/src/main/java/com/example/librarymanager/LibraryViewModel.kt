package com.example.librarymanager

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import com.example.library.LibraryItem
import com.example.library.LibraryManager

data class ScrollPosition(val index: Int, val offset: Int)

class LibraryViewModel : ViewModel() {
    private val libraryManager = LibraryManager()

    var items by mutableStateOf(libraryManager.getAllItems())
        private set

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

    fun addItem(item: LibraryItem) {
        libraryManager.addItem(item)
        items = libraryManager.getAllItems()
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