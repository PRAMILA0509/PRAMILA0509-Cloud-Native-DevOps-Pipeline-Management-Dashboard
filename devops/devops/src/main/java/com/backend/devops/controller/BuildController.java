package com.backend.devops.controller;

import com.backend.devops.model.Build;
import com.backend.devops.model.Repo;
import com.backend.devops.service.BuildService;
import com.backend.devops.service.RepoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repos/{repoId}/builds")
@CrossOrigin(origins = "http://localhost:3000")
public class BuildController {

    private final BuildService buildService;
    private final RepoService repoService;

    public BuildController(BuildService buildService, RepoService repoService) {
        this.buildService = buildService;
        this.repoService = repoService;
    }

    @PostMapping
    public Build addBuild(@PathVariable Long repoId, @RequestBody Build build) {
        Repo repo = repoService.getRepoById(repoId);
        build.setRepo(repo);
        return buildService.saveBuild(build);
    }

    @GetMapping
    public List<Build> getBuilds(@PathVariable Long repoId) {
        Repo repo = repoService.getRepoById(repoId);
        return buildService.getLastBuilds(repo);
    }
}
