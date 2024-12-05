package model.items;

/**
 * Represents an abstract book in a library.
 * This class provides the basic attributes and methods
 * necessary for managing a book, including its availability
 * and rarity status.
 */
public abstract class Book implements LibraryItem {
    // Variables
    private final String author;
    private final String title;
    private boolean available;
    private boolean rare; // Tracks if the book is rare
    private String rarityReason; // Explains why the book is rare

    // Constructor
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
        this.rare = false; // Default: not rare
        this.rarityReason = "";
    }

    // Getters
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
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", available=" + available +
                ", rare=" + rare +
                ", rarityReason='" + rarityReason + '\'' +
                '}';
    }
}

