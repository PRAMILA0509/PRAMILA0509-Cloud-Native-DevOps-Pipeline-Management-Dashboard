package com.backend.devops.controller;

import com.backend.devops.model.User;
import com.backend.devops.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Sign-up
    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Login (no JWT yet)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loggedIn = userService.login(user.getEmail(), user.getPassword());
        if (loggedIn == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
        return ResponseEntity.ok(loggedIn);
    }

    // Get all users (optional, admin view)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
