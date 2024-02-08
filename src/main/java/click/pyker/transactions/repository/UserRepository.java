package click.pyker.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import click.pyker.transactions.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
   
}
