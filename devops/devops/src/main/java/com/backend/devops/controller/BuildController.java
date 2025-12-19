package com.backend.devops.controller;

import com.backend.devops.model.Build;
import com.backend.devops.service.BuildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repos/{repoId}/builds")
@CrossOrigin(origins = "http://localhost:3000")
public class BuildController {

    private final BuildService buildService;

    public BuildController(BuildService buildService) {
        this.buildService = buildService;
    }

    // Add a build (pipeline run) for a repo
    @PostMapping
    public ResponseEntity<Build> addBuild(@PathVariable Long repoId, @RequestBody Build build) {
        Build savedBuild = buildService.addBuildToRepo(repoId, build);
        if (savedBuild == null) return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBuild);
    }

    // Get builds for a repo (latest builds)
    @GetMapping
    public ResponseEntity<List<Build>> getBuilds(@PathVariable Long repoId) {
        List<Build> builds = buildService.getBuildsForRepo(repoId);
        return ResponseEntity.ok(builds);
    }
}
