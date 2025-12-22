package com.backend.devops.service;

import com.backend.devops.model.Repo;
import com.backend.devops.repository.RepoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepoService {

    private final RepoRepository repoRepository;

    public RepoService(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public Repo saveRepo(Repo repo) {
        // Optional: check duplicate repo by name
        if (repoRepository.existsByName(repo.getName())) {
            throw new IllegalArgumentException("Repository already exists");
        }
        return repoRepository.save(repo);
    }

    public List<Repo> getAllRepos() {
        return repoRepository.findAll();
    }

    public Repo getRepoById(Long id) {
        return repoRepository.findById(id).orElse(null);
    }

    public Repo getRepoByName(String name) {
        return repoRepository.findByName(name).orElse(null);
    }
}
