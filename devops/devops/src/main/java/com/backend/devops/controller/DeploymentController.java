package com.backend.devops.controller;

import com.backend.devops.model.*;
import com.backend.devops.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/deployments")
@CrossOrigin(origins = "http://localhost:3000")
public class DeploymentController {

    private final DeploymentService deploymentService;
    private final RepoService repoService;
    private final BuildService buildService;

    public DeploymentController(DeploymentService deploymentService, RepoService repoService, BuildService buildService) {
        this.deploymentService = deploymentService;
        this.repoService = repoService;
        this.buildService = buildService;
    }

    /**
     * Trigger deployment from GitHub webhook
     */
    @PostMapping("/trigger")
    public ResponseEntity<Deployment> triggerDeploymentFromWebhook(@RequestBody Map<String, Object> payload) {

        String ref = (String) payload.get("ref"); // e.g., "refs/heads/main"
        String branch = ref.split("/")[2];       // Extract branch name

        // Map branch to repoId
        Long repoId = repoService.getRepoIdByBranch(branch);
        Long buildId = buildService.getLatestBuildIdForBranch(branch);

        // Map branch to Environment enum
        Environment environment;
        switch (branch.toLowerCase()) {
            case "dev": environment = Environment.DEV; break;
            case "staging": environment = Environment.STAGING; break;
            case "main": case "prod": environment = Environment.PROD; break;
            default:
                throw new IllegalArgumentException("Unknown branch for deployment: " + branch);
        }

        Deployment deployment = deploymentService.triggerDeployment(
                repoService.getRepoById(repoId),
                buildService.getBuildById(buildId),
                environment
        );

        return ResponseEntity.ok(deployment);
    }

    @PostMapping("/{deploymentId}/status")
    public ResponseEntity<Deployment> updateStatus(
            @PathVariable Long deploymentId,
            @RequestParam DeploymentStatus status
    ) {
        Deployment deployment = deploymentService.updateDeploymentStatus(deploymentId, status);
        return ResponseEntity.ok(deployment);
    }

    @GetMapping("/latest")
    public ResponseEntity<Deployment> getLatest(
            @RequestParam Long repoId,
            @RequestParam String environment
    ) {
        Repo repo = repoService.getRepoById(repoId);
        Environment env = Environment.valueOf(environment.toUpperCase());

        return deploymentService.getLatestDeployment(repo, env)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/build/{buildId}")
    public ResponseEntity<java.util.List<Deployment>> getDeploymentsForBuild(@PathVariable Long buildId) {
        Build build = buildService.getBuildById(buildId);
        return ResponseEntity.ok(deploymentService.getDeploymentsForBuild(build));
    }
}
