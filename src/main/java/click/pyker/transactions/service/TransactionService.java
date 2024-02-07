package click.pyker.transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import click.pyker.transactions.model.Transaction;
import click.pyker.transactions.model.User;
import click.pyker.transactions.repository.TransactionRepository;
import click.pyker.transactions.repository.UserRepository;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // Return a list of all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Return a transaction based on the given ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    // Create a new transaction and return the saved transaction
    public Transaction createTransaction(Transaction transactionRequest) {
        
        User sender = userRepository.findById(transactionRequest.getSender()).orElse(null);
        User receiver = userRepository.findById(transactionRequest.getReceiver()).orElse(null);

        if (sender != null && receiver != null) {
            Transaction newTransaction = new Transaction(sender.getId(), receiver.getId(), transactionRequest.getAmount());
            return transactionRepository.save(newTransaction);
        } else {
            logger.debug("Handle if sender or receiver is not found");
            return null; // Handle if sender or receiver is not found
        }
    }

    // Updates an existing transaction based on the given ID and new transaction data
    public Transaction updateTransaction(Long id, Transaction transactionRequest) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null) {
            User sender = userRepository.findById(transactionRequest.getSender()).orElse(null);
            User receiver = userRepository.findById(transactionRequest.getReceiver()).orElse(null);

            if (sender != null && receiver != null) {
                existingTransaction.setSender(sender.getId());
                existingTransaction.setReceiver(receiver.getId());
                existingTransaction.setAmount(transactionRequest.getAmount());
                return transactionRepository.save(existingTransaction);
            } else {
                logger.debug("Handle if sender or receiver is not found");
                return null; // Handle if sender or receiver is not found
            }
        } else {
            logger.debug("Handle if transaction is not found");
            return null; // Handle if transaction is not found
        }
    }

    // Deletes a transaction based on the given ID
    public boolean deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

