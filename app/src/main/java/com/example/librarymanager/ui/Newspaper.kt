package com.example.library

import com.example.librarymanager.ui.Digitalizable

class Newspaper(
    id: Int,
    available: Boolean,
    override val name: String, // Добавлено override
    val issueNumber: Int,
    val releaseMonth: Int
) : LibraryItem(id, available, name), Borrowable, Readable, Digitalizable { // Добавлена реализация интерфейса Digitalizable
    private fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "Январь"
            2 -> "Февраль"
            3 -> "Март"
            4 -> "Апрель"
            5 -> "Май"
            6 -> "Июнь"
            7 -> "Июль"
            8 -> "Август"
            9 -> "Сентябрь"
            10 -> "Октябрь"
            11 -> "Ноябрь"
            12 -> "Декабрь"
            else -> "Неверный месяц"
        }
    }

    override fun getDetailedInfo(): String {
        val monthName = getMonthName(releaseMonth)
        return "выпуск: $issueNumber газеты $name за $monthName с id: $id доступен: ${if (available) "Да" else "Нет"}"
    }

    override fun getShortInfo(): String {
        return "Газета \"$name\""
    }

    override fun getDigitalInfo(): String { // Реализация метода getDigitalInfo()
        val monthName = getMonthName(releaseMonth)
        return "Газета \"$name\", выпуск №$issueNumber за $monthName"
    }

    override fun borrow() {
        println("Нельзя взять домой")
    }

    override fun readInLibrary() {
        if (available) {
            available = false
            println("Газету $name взяли в читальный зал")
        } else {
            println("Нельзя взять в читальный зал")
        }
    }

    override fun returnItem() {
        if (!available) {
            available = true
            println("Газета $name возвращена")
        } else {
            println("Эта газета уже доступна")
        }
    }
}