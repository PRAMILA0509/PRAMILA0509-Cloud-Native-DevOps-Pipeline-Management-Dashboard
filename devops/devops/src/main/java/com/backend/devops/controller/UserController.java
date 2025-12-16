package com.backend.devops.controller;

import com.backend.devops.model.User;
import com.backend.devops.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // SIGN UP
    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    // LOGIN (NO JWT)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return ResponseEntity.ok(
                userService.login(user.getEmail(), user.getPassword())
        );
    }
}
