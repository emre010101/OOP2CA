import exception.ItemNotAvailableException;
import exception.ItemNotFoundException;
import exception.UserNotFoundException;
import model.Loan;
import model.items.Dvd;
import model.items.FictionBook;
import model.items.LibraryItem;
import model.items.NonFictionBook;
import model.user.Member;
import model.user.Staff;
import model.user.User;
import service.LibraryItemService;
import service.LoanService;
import service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Library {
    private final UserService userService;
    private final LibraryItemService libraryItemService;
    private final LoanService loanService;

    // Constructor to initialize services
    public Library() {
        this.userService = new UserService();
        this.libraryItemService = new LibraryItemService();
        this.loanService = new LoanService();

        // Create default loans
        createDefaultLoans();
    }
    private void createDefaultLoans() {
        // Fetch the default member
        Member defaultMember = (Member) userService.findUserById(2L); // The default member has ID 2
        if (defaultMember == null) {
            System.out.println("Default member not found. Skipping loan creation.");
            return;
        }

        // Fetch two items from the library
        LibraryItem firstItem = libraryItemService.findItemByTitle("The Great Gatsby");
        LibraryItem secondItem = libraryItemService.findItemByTitle("Inception");

        // Create loans if items are available
        if (firstItem != null && firstItem.isAvailable()) {
            loanService.createLoan(defaultMember, firstItem);
        }
        if (secondItem != null && secondItem.isAvailable()) {
            loanService.createLoan(defaultMember, secondItem);
        }
    }


    public static void main(String[] args) {
        User activeUser; //Member or staff using the system
        Scanner scanner = new Scanner(System.in); //One instance of scanner will be used in all the methods
        Library library = new Library(); //Creating instance of library be able to call non-static methods
        //Who is using the system need to be learned first
        activeUser = library.authenticateUser(scanner);
        library.runMenu(activeUser);
    }

    private User authenticateUser(Scanner scanner) {
        System.out.println("Welcome! Please log in to the system.");
        String userType = InputUtils.getValidInput(
                "Enter user type (member/staff)",
                Arrays.asList("member", "staff"),
                scanner
        );

        if (userType == null) {
            System.out.println("Goodbye!");
            System.exit(0); // Exit the program
        }

        System.out.print("Enter your name or ID: ");
        String identifier = scanner.nextLine();

        try {
            // Try to find user by ID or name
            User activeUser = userService.findUser(userType, identifier);
            System.out.println("Welcome, " + activeUser.getName() + "!");
            return activeUser; // Exit the loop if authentication is successful
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Goodbye!");
            System.exit(0); // Exit the program
        }
        return null;
    }


    private void runMenu(User activeUser) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            //Each type of user have different privileges
            if(activeUser instanceof Staff) {
                System.out.println("1. Add User");
                System.out.println("2. List Users");
                System.out.println("3. List Items");
                System.out.println("4. Add Item");
                System.out.println("5. View Active Loans");
                System.out.println("q. Quit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> addUser(scanner);
                    case "2" -> listUsers();
                    case "3" -> listItems();
                    case "4" -> addItem(scanner);
                    case "5" -> viewActiveLoans(scanner);
                    case "q" -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }else{ //If it's a member it will have different options
                System.out.println("1. List Items");
                System.out.println("2. Borrow Item");
                System.out.println("3. Return Item");
                System.out.println("4. View Active Loans for Active User");
                System.out.println("q. Quit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> listItems();
                    case "2" -> borrowItem(scanner, activeUser);
                    case "3" -> returnItem(scanner, activeUser);
                    case "4" -> viewActiveLoansForUser(scanner, activeUser);
                    case "q" -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }



        }

        scanner.close();
        System.out.println("Exiting Library Management System. Goodbye!");
    }


    /**
     * For loop replaced with functional interface Consumer
     * Consumer<T> : Accepts an input and performs an operation, but doesn't return a result
     */
    private void listItems() {
        List<LibraryItem> items = libraryItemService.getLibraryItems();
        Consumer<LibraryItem> printItem = System.out::println;
        items.forEach(printItem);
//        for (LibraryItem item : items) {
//            System.out.println(item.toString());
//        }
    }

    private void listUsers() {
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
    }
    private void addUser(Scanner scanner) {
        String userType = InputUtils.getValidInput(
                "Enter user type (member/staff)",
                Arrays.asList("member", "staff"),
                scanner
        );

        if (userType == null) {
            System.out.println("Returning to the main menu...");
            return; // User chose to quit
        }

        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter user ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        if (userType.equalsIgnoreCase("member")) {
            userService.addUser(new Member(name, id));
            System.out.println("Member added successfully!");
        } else if (userType.equalsIgnoreCase("staff")) {
            System.out.print("Enter join date (yyyy-mm-dd): ");
            String joinDate = scanner.nextLine();
            userService.addUser(new Staff(name, id, LocalDate.parse(joinDate)));
            System.out.println("Staff added successfully!");
        }
    }

    private void addItem(Scanner scanner) {
        String itemType = InputUtils.getValidInput(
                "Enter item type (book/dvd)",
                Arrays.asList("book", "dvd"),
                scanner
        );

        if (itemType == null) {
            System.out.println("Returning to the main menu...");
            return; // User chose to quit
        }

        if (itemType.equals("book")) {
            addBook(scanner); // Delegate to addBook method
        } else if (itemType.equals("dvd")) {
            addDvd(scanner); // Delegate to addDvd method
        }
    }

    private void addBook(Scanner scanner) {
        String bookType = InputUtils.getValidInput(
                "Enter book type (fiction/nonfiction)",
                Arrays.asList("fiction", "nonfiction"),
                scanner
        );

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        if (bookType == null) {
            System.out.println("Returning to the main menu...");
            return; // User chose to quit
        }

        if (bookType.equals("fiction")) {
            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();
            libraryItemService.addItem(new FictionBook(title, author, genre));
        } else if (bookType.equals("nonfiction")) {
            System.out.print("Enter subject: ");
            String subject = scanner.nextLine();
            System.out.print("Enter year of publication: (2012)");
            int year = Integer.parseInt(scanner.nextLine());
            libraryItemService.addItem(new NonFictionBook(title, author, subject, year));
        }

        System.out.println("Book added successfully!");
    }

    private void addDvd(Scanner scanner) {
        System.out.print("Enter DVD title: ");
        String title = scanner.nextLine();
        System.out.print("Enter director: ");
        String director = scanner.nextLine();
        System.out.print("Enter duration (in minutes): ");
        int duration = Integer.parseInt(scanner.nextLine());

        libraryItemService.addItem(new Dvd(title, director, duration));
        System.out.println("DVD added successfully!");
    }

    private void borrowItem(Scanner scanner, User activeUser) {
        try {
            System.out.print("Enter item title: ");
            String bookTitle = scanner.nextLine().trim();

            LibraryItem libraryItem = libraryItemService.findItemByTitle(bookTitle);

            if (libraryItem == null) {
                throw new ItemNotFoundException(bookTitle + " not found.");
            }else if(!libraryItem.isAvailable()){
                throw new ItemNotAvailableException(bookTitle + " is not available.");
            }else{
                Loan _ = loanService.createLoan((Member) activeUser, libraryItem);
                System.out.println("Book borrowed successfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param scanner
     * @param activeUser
     */
    private void returnItem(Scanner scanner, User activeUser) {
        try {
            Member member = (Member) activeUser;

            // Fetch active loans for the member
            List<Loan> loans = loanService.getLoansForMember(member);

            // Check if there are any active loans
            if (loans.isEmpty()) {
                System.out.println("You have no active loans to return.");
                return;
            }

            // Display the active loans
            System.out.println("Active Loans:");
            var listOptions = new ArrayList<String>(); //Local Variable Type Inference
            for (int i = 0; i < loans.size(); i++) {
                listOptions.add(String.valueOf(i+1));
                Loan loan = loans.get(i);
                System.out.println((i + 1) + ". " + loan.getBook().getTitle() +
                        " (Due Date: " + loan.getDueDate() + ")");
            }

            // Prompt the user to select a loan to return using utils
            String userChoice = InputUtils.getValidInput("Enter the number of the item you want to return: ", listOptions, scanner);
            // Validate the choice
            if (userChoice == null) {
                System.out.println("Invalid choice. Please try again.");
                return;
            }

            int choice = Integer.parseInt(userChoice);

            // Get the selected loan and complete it
            Loan selectedLoan = loans.get(choice - 1);
            loanService.completeLoan(selectedLoan);

            System.out.println("Item returned successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    private void viewActiveLoans(Scanner scanner) {
        /* User already authenticated so this logic will be skipped
        System.out.print("Enter member ID: ");
        Long memberId = Long.parseLong(scanner.nextLine());

        Member member = (Member) userService.findUserById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;*/

        List<Loan> activeLoans = loanService.getActiveLoans();
        if (activeLoans.isEmpty()) {
            System.out.println("No active loans for any member.");
        } else {
            System.out.println("Active Loans:");
            for (Loan loan : activeLoans) {
                System.out.println(loan);
            }
        }
    }
    private void viewActiveLoansForUser(Scanner scanner, User activeUser) {
        List<Loan> activeLoans = loanService.getLoansForMember((Member)activeUser);//Casting to member
        if (activeLoans.isEmpty()) {
            System.out.println("No active loans for member: " + activeUser.getName());
        } else {
            System.out.println("Active Loans:");
            for (Loan loan : activeLoans) {
                System.out.println(loan);
            }
        }
    }

}