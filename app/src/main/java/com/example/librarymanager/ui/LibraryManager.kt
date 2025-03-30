package com.example.library

class LibraryManager {
    private val items = mutableListOf<LibraryItem>()

    init {
        items.add(Book(1, true, "Мы", 224, "Евгений Замятин"))
        items.add(Book(2, true, "1984", 320, "Джордж Оруэлл"))
        items.add(Book(3, false, "451 градус по Фаренгейту", 224, "Рэй Брэдбери"))
        items.add(Book(4, true, "О дивный новый мир", 351, "Олдос Хаксли"))
        items.add(Newspaper(5, false, "Правда", 1912, 5))
        items.add(Newspaper(6, true, "Известия", 2023, 3))
        items.add(Newspaper(7, true, "Коммерсантъ", 2024, 12))
        items.add(Disc(8, true, "Автостопом по галактике", "DVD"))
        items.add(Disc(9, true, "Американский психопат", "DVD"))
        items.add(Disc(10, false, "Лучшие песни MACAN", "CD"))
    }

    fun showItems(type: String) {
        val filteredItems = when (type) {
            "книги" -> items.filterIsInstance<Book>()
            "газеты" -> items.filterIsInstance<Newspaper>()
            "диски" -> items.filterIsInstance<Disc>()
            else -> items
        }
        filteredItems.forEachIndexed { index, item ->
            println("${index + 1}. ${item.getShortInfo()}")
        }
    }

    fun getAllItems(): List<LibraryItem> = items.toList()

    fun selectItem(index: Int, type: String): LibraryItem? {
        val filteredItems = when (type) {
            "книги" -> items.filterIsInstance<Book>()
            "газеты" -> items.filterIsInstance<Newspaper>()
            "диски" -> items.filterIsInstance<Disc>()
            else -> items
        }
        return filteredItems.getOrNull(index - 1)
    }

    fun takeHome(item: LibraryItem) {
        if (item is Borrowable) {
            item.borrow()
        } else {
            println("Нельзя взять домой")
        }
    }

    fun readInLibrary(item: LibraryItem) {
        if (item is Readable) {
            item.readInLibrary()
        } else {
            println("Нельзя читать (смотреть) в зале")
        }
    }

    fun returnItem(item: LibraryItem) {
        if (item is Borrowable) {
            item.returnItem()
        } else if (item.available) {
            println("Этот объект уже доступен")
        } else {
            println("Вы не брали этот объект")
        }
    }

    fun addDigitizedItem(disc: Disc) {
        items.add(disc)
    }
}