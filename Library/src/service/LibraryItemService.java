package service;

import model.items.Dvd;
import model.items.FictionBook;
import model.items.LibraryItem;
import model.items.NonFictionBook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LibraryItemService {
    private List<LibraryItem> libraryItems;

    public LibraryItemService() {
        this.libraryItems = new ArrayList<>();

        // Add default FictionBooks
        FictionBook gatsby = new FictionBook("The Great Gatsby", "F. Scott Fitzgerald", "Classic");
        gatsby.setRare(true, "Limited first edition copy");
        libraryItems.add(gatsby);

        FictionBook dystopia = new FictionBook("1984", "George Orwell", "Dystopian");
        dystopia.setRare(false, "");
        libraryItems.add(dystopia);

        libraryItems.add(new FictionBook("To Kill a Mockingbird", "Harper Lee", "Social"));
        libraryItems.add(new FictionBook("Harry Potter", "J.K. Rowling", "Fantasy"));
        libraryItems.add(new FictionBook("The Hobbit", "J.R.R. Tolkien", "Adventure"));

        // Add default NonFictionBooks
        NonFictionBook sapiens = new NonFictionBook("Sapiens", "Yuval Noah Harari", "History", 2014);
        sapiens.setRare(true, "Signed by the author");
        libraryItems.add(sapiens);

        NonFictionBook thinkingFast = new NonFictionBook("Thinking, Fast and Slow", "Daniel Kahneman", "Psychology", 2011);
        thinkingFast.setRare(false, "");
        libraryItems.add(thinkingFast);

        libraryItems.add(new NonFictionBook("Educated", "Tara Westover", "Memoir", 2018));
        libraryItems.add(new NonFictionBook("The Immortal Life of Henrietta Lacks", "Rebecca Skloot", "Biography", 2010));
        libraryItems.add(new NonFictionBook("The Wright Brothers", "David McCullough", "Biography", 2015));

        // Add default DVDs
        libraryItems.add(new Dvd("Inception", "Christopher Nolan", 148));
        libraryItems.add(new Dvd("The Matrix", "The Wachowskis", 136));
        libraryItems.add(new Dvd("The Godfather", "Francis Ford Coppola", 175));
        libraryItems.add(new Dvd("Forrest Gump", "Robert Zemeckis", 142));
        libraryItems.add(new Dvd("The Shawshank Redemption", "Frank Darabont", 142));

        System.out.println("Default library items added.");
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

    public List<LibraryItem> getLibraryItems(Boolean available) {
        if (available) {
            return new ArrayList<>(libraryItems.stream().filter(LibraryItem::isAvailable).toList());
        }else{
            return new ArrayList<>(libraryItems);
        }
    }

    // Check availability
    public boolean isItemAvailable(String title) {
        LibraryItem libraryItem = findItemByTitle(title);
        // if the item is not found return false, otherwise return item.isAvailable property
        return libraryItem != null && libraryItem.isAvailable();
    }

    // Remove an item with their title
    public boolean removeItem(String title) {
        return libraryItems.removeIf(item -> item.getTitle().equals(title));
    }

    public List<LibraryItem> searchItemByTitle(String itemTitle) {
        Predicate<LibraryItem> containsCharinTitle = libraryItem -> libraryItem.getTitle().contains(itemTitle);
        return libraryItems.stream()
                .filter(containsCharinTitle)
                .toList();
    }
}