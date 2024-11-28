package User;

public abstract class User {
    private String name;
    private Long id;

    // Constructor
    public User(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    // Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Abstract method to determine levels
    public abstract String getLevel();

    // Abstract method to update levels if applicable
    public abstract void updateLevel();
    
    
}
