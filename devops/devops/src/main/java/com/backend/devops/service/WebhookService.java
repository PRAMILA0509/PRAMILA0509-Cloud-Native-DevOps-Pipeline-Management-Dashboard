package com.backend.devops.service;

import com.backend.devops.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class WebhookService {

    private final RepoService repoService;
    private final BuildService buildService;
    private final DeploymentService deploymentService;

    public WebhookService(
            RepoService repoService,
            BuildService buildService,
            DeploymentService deploymentService
    ) {
        this.repoService = repoService;
        this.buildService = buildService;
        this.deploymentService = deploymentService;
    }

    public void processWebhook(Map<String, Object> payload) {


        Map<String, Object> repository =
                (Map<String, Object>) payload.get("repository");
        if (repository == null) return;

        String repoName = (String) repository.get("name");
        if (repoName == null) return;

        Repo repo = repoService.findRepoByNameOrNull(repoName);
        if (repo == null) {
            System.out.println("Webhook ignored: repo not registered -> " + repoName);
            return;
        }


        Map<String, Object> workflowRun =
                (Map<String, Object>) payload.get("workflow_run");
        if (workflowRun == null) return;

        String statusRaw = (String) workflowRun.get("status");       // in_progress / completed
        String conclusion = (String) workflowRun.get("conclusion"); // success / failure
        String sha = (String) workflowRun.get("head_sha");
        String branch = (String) workflowRun.get("head_branch");

        BuildStatus buildStatus = mapStatus(statusRaw, conclusion);


        Map<String, Object> headCommit =
                (Map<String, Object>) workflowRun.get("head_commit");

        String commitMessage = headCommit != null
                ? (String) headCommit.get("message")
                : "No commit message";

        Map<String, Object> author =
                headCommit != null ? (Map<String, Object>) headCommit.get("author") : null;

        String commitAuthor = author != null
                ? (String) author.get("name")
                : "Unknown";

        Build build = new Build(
                repo,
                sha,
                commitAuthor,
                commitMessage,
                branch,
                buildStatus,
                LocalDateTime.now()
        );
        build.setBranch(branch);

        if (buildStatus != BuildStatus.RUNNING) {
            build.setEndTime(LocalDateTime.now());
        }

        buildService.saveBuild(build);
        System.out.println("✅ Build saved: " + buildStatus);


        if (buildStatus == BuildStatus.SUCCESS) {
            deploymentService.autoDeployToDev(repo, build);
            System.out.println("🚀 Auto-deployed to DEV");
        }
    }

    private BuildStatus mapStatus(String status, String conclusion) {

        if ("in_progress".equalsIgnoreCase(status)) {
            return BuildStatus.RUNNING;
        }

        if (conclusion == null) {
            return BuildStatus.UNKNOWN;
        }

        return switch (conclusion.toLowerCase()) {
            case "success" -> BuildStatus.SUCCESS;
            case "failure" -> BuildStatus.FAILED;
            case "cancelled" -> BuildStatus.CANCELLED;
            default -> BuildStatus.UNKNOWN;
        };
    }
}
