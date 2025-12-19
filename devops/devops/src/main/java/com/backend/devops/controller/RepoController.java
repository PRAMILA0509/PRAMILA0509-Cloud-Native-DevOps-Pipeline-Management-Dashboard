package com.backend.devops.controller;

import com.backend.devops.model.Repo;
import com.backend.devops.service.RepoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repos")
@CrossOrigin(origins = "http://localhost:3000")
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    // Create repo
    @PostMapping
    public ResponseEntity<Repo> createRepo(@RequestBody Repo repo) {
        Repo savedRepo = repoService.saveRepo(repo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRepo);
    }

    // Get all repos
    @GetMapping
    public ResponseEntity<List<Repo>> getAllRepos() {
        return ResponseEntity.ok(repoService.getAllRepos());
    }

    // Get repo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Repo> getRepoById(@PathVariable Long id) {
        Repo repo = repoService.getRepoById(id);
        if (repo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(repo);
    }
}
