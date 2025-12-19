package com.backend.devops.repository;

import com.backend.devops.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRepository extends JpaRepository<Repo, Long> {

    // Find repo by name
    Optional<Repo> findByName(String name);

    // Check if repo exists by name
    boolean existsByName(String name);
}
