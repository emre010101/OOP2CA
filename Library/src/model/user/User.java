package model.user;

import java.time.LocalDate;

public abstract sealed class User permits Member, Staff {
    private String name;
    private Long id;
    private LocalDate dateJoined;


    // Constructor
    public User(String name, Long id) {
        this.name = name;
        this.id = id;
        dateJoined = LocalDate.now();
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getters
    public String getName() {return name;}
    public Long getId() {
        return id;
    }
    public LocalDate getDateJoined() {return dateJoined;}

    // Abstract method to determine levels
    public abstract String getLevel();

    // Abstract method to update levels if applicable
    public abstract void updateLevel();

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", dateJoined=" + dateJoined +
                '}';
    }
}
