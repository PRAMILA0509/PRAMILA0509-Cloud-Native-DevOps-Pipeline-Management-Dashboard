package com.backend.devops.controller;

import com.backend.devops.model.Build;
import com.backend.devops.model.Repo;
import com.backend.devops.service.BuildService;
import com.backend.devops.service.RepoService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin(origins = "http://localhost:3000")
public class WebhookController {

    private final RepoService repoService;
    private final BuildService buildService;

    public WebhookController(RepoService repoService, BuildService buildService) {
        this.repoService = repoService;
        this.buildService = buildService;
    }

    @PostMapping
    public String handleWebhook(@RequestBody Map<String, Object> payload) {
        try {
            // Extract repo name
            Map<String, Object> repository = (Map<String, Object>) payload.get("repository");
            String repoName = (String) repository.get("name");

            // Find repo in DB
            Repo repo = repoService.getRepoByName(repoName);

            // Extract commit SHA
            Map<String, Object> workflowRun = (Map<String, Object>) payload.get("workflow_run");
            String sha = (String) workflowRun.get("head_sha");

            // Extract status
            String status = (String) workflowRun.get("conclusion"); // success / failure / cancelled
            if (status == null) status = "PENDING";

            // Optional message
            String message = (String) workflowRun.get("name");

            // Timestamp
            String timestampStr = (String) workflowRun.get("updated_at");
            LocalDateTime timestamp = LocalDateTime.parse(timestampStr.replace("Z", ""));

            // Save build
            Build build = new Build(repo, sha, status.toUpperCase(), message, timestamp);
            buildService.saveBuild(build);

            return "Webhook received";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing webhook";
        }
    }
}
