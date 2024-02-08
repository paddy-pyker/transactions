package click.pyker.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import click.pyker.transactions.exception.TransactionNotFoundException;
import click.pyker.transactions.exception.InvalidTransactionException;
import click.pyker.transactions.model.Transaction;
import click.pyker.transactions.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    // Return a paginated list of transactions
    @GetMapping("/")
    public ResponseEntity<Page<Transaction>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Transaction> transactions = transactionService.getAllTransactions(page, size);
        return ResponseEntity.ok(transactions);
    }

    // Return a transaction based on the given ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        
        return ResponseEntity.ok(transaction);   
    } 

    // Create a new transaction and return the saved transaction
    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transactionRequest) {
        if (transactionRequest.isValid()){
            Transaction savedTransaction = transactionService.createTransaction(transactionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } else {
            throw new InvalidTransactionException("Invalid transaction data provided.");
        }
        
    }

    // Updates an existing transaction based on the given ID and new transaction data
    @PutMapping("/update/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionRequest) {
        if (transactionRequest.isValid()){
            
            Transaction updatedTransaction = transactionService.updateTransaction(id, transactionRequest);
            return ResponseEntity.ok(updatedTransaction);

        } else {
            throw new InvalidTransactionException("Invalid transaction data provided.");
        }
    }

    // Deletes a transaction based on the given ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }
    }
}
