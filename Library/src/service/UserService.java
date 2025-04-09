package service;

import exception.UserNotFoundException;
import model.records.UserRecord;
import model.user.Member;
import model.user.Staff;
import model.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    // will hold all the users in the library system
    private List<User> users;

    //Initialising user service with one each type of default user added
    public UserService() {
        this.users = new ArrayList<>();

        // Add a default Staff user
        Staff defaultStaff = new Staff("Admin Staff", 1L, LocalDate.now());
        users.add(defaultStaff);

        // Add a default Member user
        Member defaultMember = new Member("Default Member", 2L);
        users.add(defaultMember);

        System.out.println("Default staff and member added.");
    }

    //Method to find user from their name or id
    public User findUser(String userType, String identifier) throws UserNotFoundException {
        User user;

        // Try to parse identifier as ID
        try {
            long id = Long.parseLong(identifier);
            user = findUserById(id);
        } catch (NumberFormatException e) {
            // If parsing fails, treat identifier as a name
            user = findUserByName(identifier);
        }

        // Check if the user is found and matches the type
        if (user == null ||
                (userType.equals("member") && !(user instanceof Member)) ||
                (userType.equals("staff") && !(user instanceof Staff))) {
            throw new UserNotFoundException("User not found or does not match the specified type.");
        }
        return user;
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
    public List<UserRecord> getAllUsers() {
        return users.stream()
                .map(user -> {
                    String type = (user instanceof Member) ? "Member" : "Staff";
                    String level = user.getLevel().toString();
                    return new UserRecord(user.getName(), type, level);
                })
                .toList();
    }


}
