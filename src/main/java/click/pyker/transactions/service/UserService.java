package click.pyker.transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.pyker.transactions.model.User;
import click.pyker.transactions.repository.UserRepository;

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
    public User createNewUser(String username) {
        
        User newuUser = new User(username);
        return userRepository.save(newuUser);
       
    }
}
