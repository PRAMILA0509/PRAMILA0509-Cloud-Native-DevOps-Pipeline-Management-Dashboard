package com.backend.devops.controller;

import com.backend.devops.model.Repo;
import com.backend.devops.service.RepoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repos")
//@CrossOrigin(origins = "http://localhost:3000")
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    // ✅ Register repo for a user (team auto-created)
    @PostMapping("/register/{userId}")
    public ResponseEntity<Repo> registerRepo(
            @PathVariable Long userId,
            @RequestBody Repo repo
    ) {
        if (repo.getName() == null || repo.getUrl() == null) {
            return ResponseEntity.badRequest().build();
        }

        Repo savedRepo = repoService.registerRepoForUser(
                userId,
                repo.getName(),
                repo.getUrl(),
                repo.getOwner(),
                repo.getDefaultBranch()
        );

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
        return ResponseEntity.ok(repoService.getRepoById(id));
    }
}
