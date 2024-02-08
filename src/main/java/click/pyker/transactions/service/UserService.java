package click.pyker.transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.pyker.transactions.model.User;
import click.pyker.transactions.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Return all users 
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // Create a new user return the saved user
    public User createNewUser(String username, BigDecimal accountBalance) {
        User newuUser = new User(username,accountBalance);
        return userRepository.save(newuUser);
    }
}
