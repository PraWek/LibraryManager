//package com.example.library
//
//inline fun <reified T> List<*>.filterByType(): List<T> {
//    return filterIsInstance<T>()
//}
//
//fun demonstrateTypeFiltering() {
//    println("Реализация 4 пункта")
//    val mixedList = listOf(
//        Book(1, true, "Книга 1", 100, "Автор 1"),
//        Disc(2, true, "Диск 1", "DVD"),
//        Newspaper(3, true, "Газета 1", 2000, 1),
//        "String",
//        42
//    )
//
//    val books: List<Book> = mixedList.filterByType()
//    println("Книги: ${books.map { it.name }}")
//
//    val discs: List<Disc> = mixedList.filterByType()
//    println("Диски: ${discs.map { it.name }}")
//
//    val strings: List<String> = mixedList.filterByType()
//    println("Strings: $strings")
//
//    val numbers: List<Int> = mixedList.filterByType()
//    println("Numbers: $numbers")
//
//    println()
//    println("Реализация самой библиотеки")
//    println()
//}
