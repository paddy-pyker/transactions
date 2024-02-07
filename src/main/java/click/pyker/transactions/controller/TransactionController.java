package click.pyker.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import click.pyker.transactions.model.Transaction;
import click.pyker.transactions.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Return a list of all transactions
    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    // Return a transaction based on the given ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new transaction and return the saved transaction
    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transactionRequest) {
        Transaction savedTransaction = transactionService.createTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    // Updates an existing transaction based on the given ID and new transaction data
    @PutMapping("/update/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionRequest) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionRequest);
        if (updatedTransaction != null) {
            return ResponseEntity.ok(updatedTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletes a transaction based on the given ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}