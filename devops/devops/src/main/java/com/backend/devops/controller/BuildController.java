package com.backend.devops.controller;

import com.backend.devops.model.Build;
import com.backend.devops.service.BuildService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/builds")
//@CrossOrigin(origins = "http://localhost:3000")
public class BuildController {

    private final BuildService buildService;

    public BuildController(BuildService buildService) {
        this.buildService = buildService;
    }


    @GetMapping("/repo/{repoId}")
    public ResponseEntity<List<Build>> getBuildsForRepo(
            @PathVariable Long repoId) {
        return ResponseEntity.ok(buildService.getBuildsForRepo(repoId));
    }


    @GetMapping("/repo/{repoId}/latest")
    public ResponseEntity<Build> getLatestBuild(
            @PathVariable Long repoId) {
        return ResponseEntity.ok(
                buildService.getLatestBuildForRepo(repoId)
        );
    }
}
