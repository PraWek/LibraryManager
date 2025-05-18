package com.example.librarymanager.domain.repository

import com.example.librarymanager.domain.model.LibraryItem

interface LibraryRepository {
    suspend fun getAvailableItems(): List<LibraryItem>
    suspend fun borrowItem(id: Int): LibraryItem
    suspend fun returnItem(id: Int): LibraryItem
}
