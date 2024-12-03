import exception.ItemNotAvailableException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Library {
    private final UserService userService;
    private final LibraryItemService libraryItemService;
    private final LoanService loanService;

    // Constructor to initialize services
    public Library() {
        this.userService = new UserService();
        this.libraryItemService = new LibraryItemService();
        this.loanService = new LoanService();
    }


    public static void main(String[] args) {
        Library library = new Library();
        library.runMenu();
    }

    private void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add User");
            System.out.println("2. List Users");
            System.out.println("3. Add Item");
            System.out.println("4. Borrow Item");
            System.out.println("5. Return Item");
            System.out.println("6. View Active Loans");
            System.out.println("q. Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> addUser(scanner);
                case "2" -> listUsers();
                case "3" -> addItem(scanner);
                case "4" -> borrowItem(scanner);
                case "5" -> returnBook(scanner);
                case "6" -> viewActiveLoans(scanner);
                case "q" -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Exiting Library Management System. Goodbye!");
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
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        String bookType = InputUtils.getValidInput(
                "Enter book type (fiction/nonfiction)",
                Arrays.asList("fiction", "nonfiction"),
                scanner
        );

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
            System.out.print("Enter year of publication: ");
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



    private void borrowItem(Scanner scanner) {
        try {
            System.out.print("Enter member ID: ");
            Long memberId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter book title: ");
            String bookTitle = scanner.nextLine();

            Member member = (Member) userService.findUserById(memberId);
            if (member == null) throw new UserNotFoundException("Member not found!");

            LibraryItem libraryItem = libraryItemService.findItemByTitle(bookTitle);

            Loan loan = loanService.createLoan(member, libraryItem);
            if (loan == null) throw new ItemNotAvailableException("Book is not available!");
            System.out.println("Book borrowed successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void returnBook(Scanner scanner) {
        try {
            System.out.print("Enter member ID: ");
            Long memberId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter book title: ");
            String bookTitle = scanner.nextLine();

            Member member = (Member) userService.findUserById(memberId);
            if (member == null) throw new UserNotFoundException("Member not found!");

            //loanService.completeLoan()
            System.out.println("Book returned successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewActiveLoans(Scanner scanner) {
        System.out.print("Enter member ID: ");
        Long memberId = Long.parseLong(scanner.nextLine());

        Member member = (Member) userService.findUserById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        List<Loan> activeLoans = loanService.getActiveLoans();
        if (activeLoans.isEmpty()) {
            System.out.println("No active loans for this member.");
        } else {
            System.out.println("Active Loans:");
            for (Loan loan : activeLoans) {
                System.out.println("Book: " + loan.getBook().getTitle() + ", Due Date: " + loan.getDueDate());
            }
        }
    }
}