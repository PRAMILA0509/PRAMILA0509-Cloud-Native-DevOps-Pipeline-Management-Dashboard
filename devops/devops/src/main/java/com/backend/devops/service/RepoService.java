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
        // Validation to prevent null values
        if (repo.getName() == null || repo.getName().isEmpty()) {
            throw new IllegalArgumentException("Repo name cannot be null or empty");
        }
        if (repo.getUrl() == null || repo.getUrl().isEmpty()) {
            throw new IllegalArgumentException("Repo URL cannot be null or empty");
        }

        return repoRepository.save(repo);
    }

    public List<Repo> getAllRepos() {
        return repoRepository.findAll();
    }

    public Repo getRepoById(Long id) {
        return repoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repo not found"));
    }
    public Repo getRepoByName(String name) {
        return repoRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Repo not found"));
    }

}
