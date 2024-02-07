package click.pyker.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import click.pyker.transactions.model.Transaction;

    
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
    