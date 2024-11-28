package service;

import model.items.LibraryItem;

import java.util.ArrayList;
import java.util.List;

public class LibraryItemService {
    private List<LibraryItem> libraryItems;

    public LibraryItemService() {
        this.libraryItems = new ArrayList<>();
    }

    // Add a new item
    public void addItem(LibraryItem item) {
        libraryItems.add(item);
    }

    // Find a book by ID
    public LibraryItem findItemByTitle(String title) {
        return libraryItems.stream()
                .filter(item -> item.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    // Check availability
    public boolean isItemAvailable(String title) {
        LibraryItem book = findItemByTitle(title);
        // if the book is not found return false, otherwise return book.isAvailable property
        return book != null && book.isAvailable();
    }

    // Remove an item with their title
    public boolean removeBook(String title) {
        return libraryItems.removeIf(item -> item.getTitle().equals(title));
    }
}