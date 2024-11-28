package model.items;

public interface LibraryItem {
    String getTitle();
    boolean isAvailable();
    void setAvailable(boolean available);
}
