package com.backend.devops.repository;

import com.backend.devops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email and password (for login, no JWT)
    Optional<User> findByEmailAndPassword(String email, String password);

    // Find by email (useful for signup duplicate check)
    Optional<User> findByEmail(String email);
}
