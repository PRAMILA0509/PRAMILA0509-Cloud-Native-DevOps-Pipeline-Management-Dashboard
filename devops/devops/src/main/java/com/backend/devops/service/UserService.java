package com.backend.devops.service;

import com.backend.devops.model.User;
import com.backend.devops.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Sign-up / save user
    public User saveUser(User user) {
        // Optional: hash password here later
        return userRepository.save(user);
    }

    // Login (no JWT)
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
