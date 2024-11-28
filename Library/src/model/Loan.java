package model;

import model.items.LibraryItem;
import model.user.Member;

import java.time.LocalDate;

public class Loan {
    // Variables
    private Member member;
    private LibraryItem libraryItem;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnedDate;

    // Constructor
    public Loan(Member member, LibraryItem libraryItem, LocalDate loanDate, LocalDate dueDate) {
        this.member = member;
        this.libraryItem = libraryItem;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnedDate = null; // Not returned yet
    }

    // Getters and Setters
    public Member getMember() {
        return member;
    }

    public LibraryItem getBook() {
        return libraryItem;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    // Check if the loan is active
    public boolean isActive() {
        return returnedDate == null;
    }
}
