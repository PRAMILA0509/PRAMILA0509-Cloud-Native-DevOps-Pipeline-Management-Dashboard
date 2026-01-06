package com.backend.devops.repository;

import com.backend.devops.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoRepository extends JpaRepository<Repo, Long> {
    Optional<Repo> findByName(String name);
    Optional<Repo> findByDefaultBranch(String branch);
    Optional<Repo> findByBranches_Name(String name);
    boolean existsByName(String name);
}