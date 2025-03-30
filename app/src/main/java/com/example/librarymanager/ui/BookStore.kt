package com.example.library

class BookStore : Store<Book> {
    private val availableBooks = mutableListOf(
        Book(11, true, "Преступление и наказание", 672, "Федор Достоевский"),
        Book(12, true, "Война и мир", 1300, "Лев Толстой"),
        Book(13, true, "Мастер и Маргарита", 448, "Михаил Булгаков")
    )

    override fun getAvailableItems(): List<Book> = availableBooks.toList()

    override fun sell(item: LibraryItem): Book {
        val book = availableBooks.find { it.id == item.id }
            ?: throw IllegalArgumentException("Книга не найдена")
        availableBooks.remove(book)
        return book
    }
}
