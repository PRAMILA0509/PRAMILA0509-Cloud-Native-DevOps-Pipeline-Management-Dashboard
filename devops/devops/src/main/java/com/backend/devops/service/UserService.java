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

    public User saveUser(User user) {

        return userRepository.save(user);
    }


    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
