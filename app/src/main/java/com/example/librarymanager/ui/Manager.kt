import com.example.library.LibraryItem
import com.example.library.Store

class Manager {
    fun buy(store: Store<out LibraryItem>): LibraryItem {
        val availableItems = store.getAvailableItems()
        println("\nДоступные товары:")
        availableItems.forEachIndexed { index, item ->
            println("${index + 1}. ${item.getShortInfo()}")
        }

        println("Выберите номер товара для покупки (0 для отмены):")
        val scanner = java.util.Scanner(System.`in`)
        val choice = scanner.nextInt()

        if (choice == 0 || choice > availableItems.size) {
            throw IllegalArgumentException("Неверный выбор")
        }

        val selectedItem = availableItems[choice - 1]
        return store.sell(selectedItem)
    }
}
