package com.king.Bibliotheque.Services;

import com.king.Bibliotheque.Exceptions.RoleException;
import com.king.Bibliotheque.Exceptions.RoleNotFoundException;
import com.king.Bibliotheque.Models.Loan;
import com.king.Bibliotheque.Repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void addLoan( Loan loan){
        if (loanRepository.findById(loan.getId()) != null) {
            throw new RoleException("Title is already taken");
        }
        loan.setStatus(false);
        this.loanRepository.save(loan);
    }

    public List<Loan> search(){
        return this.loanRepository.findAll();
    }

    public Loan getLoanById(int id) {
        Optional<Loan> optionalCategory = this.loanRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    public Loan updateLoanDetails(int id, Loan updatedCategory) {
        Optional<Loan> existingCategory = loanRepository.findById(id);

        if (existingCategory.isPresent()) {
            Loan category = existingCategory.get();

            // Update category details as needed
            category.setMotif(updatedCategory.getMotif());

            // Save the updated role to the database
            return loanRepository.save(category);
        } else {
            throw new RoleNotFoundException("Category not found");
        }
    }
    public void deleteLoan(int id) {
        Optional<Loan> category = loanRepository.findById(id);
        if (category.isPresent()) {
            loanRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException("Loan not found");
        }
    }
}