
package com.example.library

import com.example.librarymanager.ui.Digitalizable

class DigitizationOffice<T> where T : LibraryItem, T : Digitalizable {
    private var discIdCounter = 1

    fun digitize(item: T): Disc {
        val digitalInfo = item.getDigitalInfo()
        return Disc(
            discIdCounter++,
            true,
            "CD с цифровой копией: ${item.name}",
            "CD"
        ).also {
            println("Создан CD диск: ${it.name} с информацией: $digitalInfo")
        }
    }

    fun digitizeAll(items: List<T>): List<Disc> {
        return items.map { digitize(it) }
    }
}
