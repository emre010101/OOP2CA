package model.items;

/**
 * The FictionBook class represents a fiction genre book and extends the Book class.
 * It focuses on attributes specific to fiction books, such as the genre.
 */
public class FictionBook extends Book {
    //the year is not really important in fictions books as it's in non-fictions
    String genre;

    public FictionBook(String title, String author, String genre) {
        super(title,  author);
        this.genre = genre;
    }

}
