package click.pyker.transactions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import click.pyker.transactions.model.User;
import click.pyker.transactions.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Return a list of all transactions
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

     // Create a new user and return the saved user
     @PostMapping("/create")
     public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
         User savedUser = userService.createNewUser(newUser.username);
         return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
     }
}
