package click.pyker.transactions.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import click.pyker.transactions.exception.InvalidTransactionException;
import click.pyker.transactions.exception.TransactionNotFoundException;
import click.pyker.transactions.model.Transaction;
import click.pyker.transactions.model.User;
import click.pyker.transactions.repository.TransactionRepository;
import click.pyker.transactions.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testGetAllTransactions() {
        
        List<Transaction> transactions = new ArrayList<>();
        Page<Transaction> expectedTransactions = new PageImpl<>(transactions);
        when(transactionRepository.findAll(PageRequest.of(0, 10))).thenReturn(expectedTransactions);

        
        Page<Transaction> actualTransactions = transactionService.getAllTransactions(0, 10);

        
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void testGetTransactionById_Success() {
        
        Long transactionId = 1L;
        Transaction expectedTransaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(expectedTransaction));

        
        Transaction actualTransaction = transactionService.getTransactionById(transactionId);

        
        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    void testGetTransactionById_NotFound() {
        
        Long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        
        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransactionById(transactionId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testCreateTransaction_Success() {
        
        Transaction transactionRequest = new Transaction(1L, 2L, new BigDecimal("10.00"));
        User sender = new User();
        sender.setAccountBalance(new BigDecimal("20.00"));
        User receiver = new User();
        receiver.setAccountBalance(new BigDecimal("30.00"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transactionRequest);

        
        Transaction savedTransaction = transactionService.createTransaction(transactionRequest);

        
        assertNotNull(savedTransaction);
    }

    @Test
    void testCreateTransaction_InvalidTransaction() {
        
        Transaction transactionRequest = new Transaction(1L, 2L, new BigDecimal("30.00"));
        User sender = new User();
        sender.setAccountBalance(new BigDecimal("20.00"));
        User receiver = new User();
        receiver.setAccountBalance(new BigDecimal("30.00"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));

        
        assertThrows(InvalidTransactionException.class, () -> {
            transactionService.createTransaction(transactionRequest);
        });
    }
}
