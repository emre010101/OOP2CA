package model.items;

/**
 * The NonFictionBook class is a representation of a non-fiction literary work.
 * It extends the Book class by adding attributes specific to non-fiction books
 * such as the subject and the year of publication.
 */
public class NonFictionBook extends Book {
    private final String subject;
    private final int year;


    public NonFictionBook(String title, String author, String subject, int year) {
        super(title, author);
        this.year = year; // as the class variable and parameter named same 'this' used to distinguish
        this.subject = subject;
    }

    private int getYear() {
        return year;
    }
    private String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return super.toString() + //include parent fields
                " NonFictionBook{" +
                "subject='" + subject + '\'' +
                ", year=" + year +
                '}';
    }
}
