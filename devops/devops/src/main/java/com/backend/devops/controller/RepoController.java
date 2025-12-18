package com.backend.devops.controller;


import com.backend.devops.model.Repo;
import com.backend.devops.service.RepoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RepoController {
    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    @PostMapping("/api/repos")
    public void createRepo(@RequestBody Repo repo){
        repoService.saveRepo(repo);
    }

    @GetMapping("/api/repos")
    public List<Repo> getRepos(){
        return repoService.getAllRepos();
    }
}
