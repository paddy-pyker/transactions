package click.pyker.transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import click.pyker.transactions.model.Transaction;
import click.pyker.transactions.model.User;
import click.pyker.transactions.repository.TransactionRepository;
import click.pyker.transactions.repository.UserRepository;
import click.pyker.transactions.exception.TransactionNotFoundException;


@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;


    // Return a paginated list of transactions
    public Page<Transaction> getAllTransactions(int page, int size) {
        return transactionRepository.findAll(PageRequest.of(page, size));
    }

    // Return a transaction based on the given ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    // Create a new transaction and return the saved transaction
    @Transactional
    public Transaction createTransaction(Transaction transactionRequest) {
        
        User sender = userRepository.findById(transactionRequest.getSender()).orElse(null);
        User receiver = userRepository.findById(transactionRequest.getReceiver()).orElse(null);

        if (sender != null && receiver != null) {
            Transaction newTransaction = new Transaction(sender.getId(), receiver.getId(), transactionRequest.getAmount());
            Transaction savedTransaction = transactionRepository.save(newTransaction);
            logger.info("new transaction with id " + savedTransaction.getId() + " created");
            return savedTransaction;

        } else {
            throw new TransactionNotFoundException("Sender or Receipient not found."); 
        }
    }

    // Updates an existing transaction based on the given ID and new transaction data
    @Transactional
    public Transaction updateTransaction(Long id, Transaction transactionRequest) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null) {
            User sender = userRepository.findById(transactionRequest.getSender()).orElse(null);
            User receiver = userRepository.findById(transactionRequest.getReceiver()).orElse(null);

            if (sender != null && receiver != null) {
                existingTransaction.setSender(sender.getId());
                existingTransaction.setReceiver(receiver.getId());
                existingTransaction.setAmount(transactionRequest.getAmount());
                logger.info("existing transaction with id " + existingTransaction.getId() + " modified");
                return transactionRepository.save(existingTransaction);
            } else {
                throw new TransactionNotFoundException("Sender or Receipient not found."); 
            }
        } else {
            throw new TransactionNotFoundException("Transaction id not found."); 
        }
    }

    // Deletes a transaction based on the given ID
    public boolean deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            logger.info("existing transaction with id " + id + " deleted");
            return true;
        } else {
            return false;
        }
    }
}

