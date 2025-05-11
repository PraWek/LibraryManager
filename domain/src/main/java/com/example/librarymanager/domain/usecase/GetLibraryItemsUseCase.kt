package com.example.librarymanager.domain.usecase

import com.example.librarymanager.domain.model.LibraryItem
import com.example.librarymanager.domain.repository.LibraryRepository

class GetLibraryItemsUseCase(private val repository: LibraryRepository) {
    suspend operator fun invoke(): List<LibraryItem> {
        return repository.getAvailableItems()
    }
}
