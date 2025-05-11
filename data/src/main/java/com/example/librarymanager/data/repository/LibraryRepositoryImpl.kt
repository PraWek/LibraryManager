package com.example.librarymanager.data.repository

import com.example.librarymanager.domain.model.LibraryItem
import com.example.librarymanager.domain.repository.LibraryRepository

class LibraryRepositoryImpl : LibraryRepository {
    override suspend fun getAvailableItems(): List<LibraryItem> {
        // Implement actual data source logic here
        return emptyList()
    }

    override suspend fun borrowItem(id: Int): LibraryItem {
        // Implement borrow logic
        throw NotImplementedError()
    }

    override suspend fun returnItem(id: Int): LibraryItem {
        // Implement return logic
        throw NotImplementedError()
    }
}
