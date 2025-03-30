package com.example.library

interface Store<T : LibraryItem> {
    fun getAvailableItems(): List<T>
    fun sell(item: LibraryItem): T
}
