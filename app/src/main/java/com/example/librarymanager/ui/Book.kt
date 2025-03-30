package com.example.library

import com.example.librarymanager.ui.Digitalizable

class Book(
    id: Int,
    available: Boolean,
    override val name: String, // Добавлено override
    val pages: Int,
    val author: String
) : LibraryItem(id, available, name), Borrowable, Readable, Digitalizable { // Добавлена реализация интерфейса Digitalizable
    override fun getDetailedInfo(): String {
        return "$name, страниц: $pages, автор: $author, id: $id, доступно: ${if (available) "Да" else "Нет"}"
    }

    override fun getShortInfo(): String {
        return "Книга \"$name\""
    }

    override fun getDigitalInfo(): String { // Реализация метода getDigitalInfo()
        return "Книга \"$name\", автор $author, количество страниц: $pages"
    }

    override fun borrow() {
        if (available) {
            available = false
            println("Книгу $name взяли домой")
        } else {
            println("Нельзя взять домой")
        }
    }

    override fun readInLibrary() {
        if (available) {
            available = false
            println("Книгу $name взяли в читальный зал")
        } else {
            println("Нельзя взять в читальный зал")
        }
    }

    override fun returnItem() {
        if (!available) {
            available = true
            println("Книга $name возвращена")
        } else {
            println("Эта книга уже доступна")
        }
    }
}