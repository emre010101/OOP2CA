package Items;

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

}

/**
 * The NonFictionBook class is a representation of a non-fiction literary work.
 * It extends the Book class by adding attributes specific to non-fiction books
 * such as the subject and the year of publication.
 */
class NonFictionBook extends Book {
    private final String subject;
    private final int year;


    public NonFictionBook(String title, String author, String subject, int year) {
        super(title, author);
        this.year = year; // as the class variable and parameter named same 'this' used to distinguish
        this.subject = subject;
    }

    public int getYear() {
        return year;
    }
    public String getSubject() {
        return subject;
    }
}

/**
 * The FictionBook class represents a fiction genre book and extends the Book class.
 * It focuses on attributes specific to fiction books, such as the genre.
 */
class FictionBook extends Book {
    //the year is not really important in fictions books as it's in non-fictions
    String genre;

    public FictionBook(String title, String author, String genre) {
        super(title,  author);
        this.genre = genre;
    }

}

