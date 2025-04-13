package com.example.librarymanager

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.library.LibraryItem
import com.example.library.LibraryManager

class LibraryViewModel : ViewModel() {
    private val libraryManager = LibraryManager()

    var items by mutableStateOf(libraryManager.getAllItems())
        private set

    var showTypeDialog by mutableStateOf(false)
        private set

    var selectedType by mutableStateOf<String?>(null)
        private set

    fun toggleTypeDialog(show: Boolean) {
        showTypeDialog = show
    }

    fun updateSelectedType(type: String?) {
        selectedType = type
    }

    fun addItem(newItem: LibraryItem) {
        items = items + newItem
        selectedType = null
    }
}
