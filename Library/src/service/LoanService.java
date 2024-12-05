package service;

import model.Loan;
import model.items.LibraryItem;
import model.user.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private final List<Loan> loans;

    public LoanService() {
        this.loans = new ArrayList<>();
    }

    public Loan createLoan(Member member, LibraryItem item) {
        if (!item.isAvailable()) {
            System.out.println("Book is not available for loan.");
            return null;
        }
        Loan loan = new Loan(member, item, LocalDate.now(), LocalDate.now().plusWeeks(2));
        loans.add(loan);
        item.setAvailable(false);
        member.incrementTotalBooksBorrowed();
        System.out.println("Loan created successfully!");
        return loan;
    }

    public void completeLoan(Loan loan) {
        loan.setReturnedDate(LocalDate.now());
        loan.getBook().setAvailable(true);
        System.out.println("Loan completed successfully!");
    }

    public List<Loan> getActiveLoans() {
        return loans.stream()
                .filter(Loan::isActive)
                .toList();
    }
    public List<Loan> getLoansForMember(Member member) {
        return getActiveLoans().stream().filter(loan -> loan.getMember().equals(member)).toList();
    }
}
