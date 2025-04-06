package model.items;

import java.util.Comparator;

public interface LibraryItem {
    String getTitle();
    boolean isAvailable();
    void setAvailable(boolean available);
    static Comparator<LibraryItem> sortByTitle(){
        return Comparator.comparing(LibraryItem::getTitle);
    };
}
