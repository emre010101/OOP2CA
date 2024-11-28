package model.items;

/**
 * Represents a DVD in a library collection.
 * This class implements the LibraryItem interface and provides
 * details about the DVD such as its title, director, duration,
 * and availability status.
 */
public class Dvd implements LibraryItem{
    private final String title;
    private final String director;
    private final int duration; // in minutes
    private boolean available;

    public Dvd(String title, String director, int duration) {
        this.title = title;
        this.director = director;
        this.duration = duration;
        this.available = true;
    }

    public String getDirector() {
        return director;
    }

    public int getDuration() {
        return duration;
    }

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
}
