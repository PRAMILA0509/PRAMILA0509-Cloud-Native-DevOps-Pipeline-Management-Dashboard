package com.backend.devops.service;

import com.backend.devops.model.Build;
import com.backend.devops.model.BuildStatus;
import com.backend.devops.model.Repo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WebhookService {

    private final RepoService repoService;
    private final BuildService buildService;
    // Add this field at the top of the class
    private final List<Map<String, Object>> webhookStore = new ArrayList<>();


    public WebhookService(RepoService repoService, BuildService buildService) {
        this.repoService = repoService;
        this.buildService = buildService;
    }

    /**
     * Process GitHub workflow_run webhook payload
     */
    @SuppressWarnings("unchecked")
    public void processWebhook(Map<String, Object> payload) {

        // ----------------- Repository -----------------
        Map<String, Object> repository =
                (Map<String, Object>) payload.get("repository");

        if (repository == null) return;

        String repoName = (String) repository.get("name");
        if (repoName == null) return;

        Repo repo = repoService.getRepoByName(repoName);
        if (repo == null) return;

        // ----------------- Workflow Run -----------------
        Map<String, Object> workflowRun =
                (Map<String, Object>) payload.get("workflow_run");

        if (workflowRun == null) return;

        String sha = (String) workflowRun.get("head_sha");
        String message = (String) workflowRun.get("name");

        // GitHub sends null when job is still running
        String conclusion = (String) workflowRun.get("conclusion");
        BuildStatus status = conclusion == null
                ? BuildStatus.RUNNING
                : BuildStatus.valueOf(conclusion.toUpperCase());

        // ----------------- Build Creation -----------------
        Build build = new Build(
                repo,
                sha,
                status,
                message,
                LocalDateTime.now()
        );

        // Set endTime if build is completed
        if (status == BuildStatus.SUCCESS || status == BuildStatus.FAILED) {
            build.setEndTime(LocalDateTime.now());
        }

        buildService.saveBuild(build);
    }

    public List<Map<String, Object>> getAllWebhooks() {
        return new ArrayList<>(webhookStore);
    }
}
