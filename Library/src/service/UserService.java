package service;

import model.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    // will hold all the users in the library system
    private List<User> users;
    
    public UserService() {
        this.users = new ArrayList<>();
    }
    
    // Add a new user
    public void addUser(User user) {
        users.add(user);
    }
    
    // Find a user by ID
    public User findUserById(long id) {
        // use of for loop
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    
    public User findUserByName(String name) {
        // use of stream
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // Remove a user
    public boolean removeUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    // List all users
    public List<User> getAllUsers() {
        return users;
    }

}
