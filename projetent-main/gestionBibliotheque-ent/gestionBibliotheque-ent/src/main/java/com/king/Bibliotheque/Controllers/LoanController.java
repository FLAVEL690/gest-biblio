package com.king.Bibliotheque.Controllers;

import com.king.Bibliotheque.Models.Loan;
import com.king.Bibliotheque.Services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class LoanController {
    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "loan", consumes = APPLICATION_JSON_VALUE)
    public void addCategory(@RequestBody Loan category){
        this.loanService.addLoan(category);
    }

    @GetMapping(path = "loan", produces = APPLICATION_JSON_VALUE)
    public List<Loan> getLoan(){
        return this.loanService.search();
    }

    @GetMapping(path = "loan/{id}", produces = APPLICATION_JSON_VALUE)
    public Loan getLoanById(@PathVariable int id){
        return this.loanService.getLoanById(id);
    }

    @PutMapping(path = "loan/{id}" ,consumes = APPLICATION_JSON_VALUE)
    public Loan updateLoan(@PathVariable int id, @RequestBody Loan updatedCategory) {
        return loanService.updateLoanDetails(id, updatedCategory);
    }

    @DeleteMapping(path = "loan/{id}")
    public void deleteLoan(@PathVariable int id) {
        loanService.deleteLoan(id);
    }

}
