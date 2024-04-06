package com.king.Bibliotheque.Repositories;

import com.king.Bibliotheque.Models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
   Optional<Loan> findById(Integer id);
}
