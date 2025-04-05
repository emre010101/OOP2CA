package model.items;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents an abstract book in a library.
 * This class provides the basic attributes and methods
 * necessary for managing a book, including its availability
 * and rarity status.
 */
public abstract class Book implements LibraryItem {
    // Variables
    private final Long id;
    private final String author;
    private final String title;
    private boolean available;
    private boolean rare; // Tracks if the book is rare
    private String rarityReason; // Explains why the book is rare

    // Constructor
    public Book(String title, String author) {
        this.id = idSupplier.get();
        this.title = title;
        this.author = author;
        this.available = true;
        this.rare = false; // Default: not rare
        this.rarityReason = "";
    }

    /**
     * Static Supplier functional interface used to supply ids
     * Static is used to share the same instance across all the book instances
     * Each entity will have sequential id
     */
    private static final Supplier<Long> idSupplier = new Supplier<>() {
        private long id = -1L; //Starting with minus to get 0 for 1st instance

        @Override
        public Long get() {
            return ++id;
        }
    };

    // Getters
    public Long getId() { return id; }
    public String getAuthor() {
        return author;
    }

    // Interface overridden methods
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }
    // Rarity-related methods
    public boolean isRare() {
        return rare;
    }

    public String getRarityReason() {
        return rarityReason;
    }

    //Book status can be changed to rare therefore additional setter for rarity
    public void setRare(boolean rare, String reason) {
        this.rare = rare;
        this.rarityReason = rare ? reason : "";
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id + '\'' +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", available=" + available +
                ", rare=" + rare +
                ", rarityReason='" + rarityReason + '\'' +
                '}';
    }
}

