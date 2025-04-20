package service;

import model.Loan;
import model.items.Book;
import model.items.LibraryItem;
import model.items.NonFictionBook;
import model.user.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LoanService {
    private final List<Loan> loans;

    public LoanService() {
        this.loans = new ArrayList<>();
    }

    /*
        * Synchronized method to ensure thread safety when creating a loan
        * This method checks if the item is available for loan and creates a new Loan object
        * It also updates the item's availability and the member's total books borrowed
        * @param member The member borrowing the item
        * @param item The item being borrowed
        * @return The created Loan object or null if the item is not available
     */
    public synchronized Loan createLoan(Member member, LibraryItem item) {
        if (!item.isAvailable()) {
            System.out.println(Thread.currentThread().getName() + ": Book is not available for loan.");
            return null;
        }
        Loan loan = new Loan(member, item, LocalDate.now(), LocalDate.now().plusWeeks(2));
        loans.add(loan);
        item.setAvailable(false);
        member.incrementTotalBooksBorrowed();
        System.out.println(Thread.currentThread().getName() + ": Loan created successfully!");
        return loan;
    }


    public void completeLoan(Loan loan) {
        loan.setReturnedDate(LocalDate.now());
        loan.getBook().setAvailable(true);
        System.out.println("Loan completed successfully!");
    }


    /**
     * Method Reference example
     * Stream Api used get stream of series and applied filter
     * Defensive copy in getter
     * @return List<Loan>
     */
    public List<Loan> getActiveLoans() {
        return new ArrayList<>(loans.stream()
                .filter(Loan::isActive)
                .toList());
    }

    /**
     * Lambdas example
     * Predicate used to have condition on loans which returns True if it's belong to user
     * Defensive copy in getter
     * @param member
     * @return List<Loan>
     */
    public List<Loan> getLoansForMember(Member member) {
        Predicate<Loan> loanPredicate = loan -> loan.getMember().equals(member);
        return new ArrayList<>(getActiveLoans().stream().filter(loanPredicate).toList());
    }
    public static void main(String[] args) {
        LibraryItem item = new NonFictionBook("Java Basics", "Alice", "Programming", 2020); // assume Book extends LibraryItem
        Member member1 = new Member("John", 1L);
        Member member2 = new Member("Sarah", 2L);

        LoanService loanService = new LoanService();

        Runnable task1 = () -> loanService.createLoan(member1, item);
        Runnable task2 = () -> loanService.createLoan(member2, item);

        Thread thread1 = new Thread(task1, "Thread-John");
        Thread thread2 = new Thread(task2, "Thread-Sarah");

        thread1.start();
        thread2.start();
    }
}
