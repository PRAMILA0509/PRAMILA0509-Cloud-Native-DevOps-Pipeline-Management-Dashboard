package com.backend.devops.websocket;

import com.backend.devops.model.BuildStatus;

import java.time.LocalDateTime;

public class BuildDTO {

    private Long id;
    private String sha;

    private String commitAuthor;
    private String commitMessage;

    private BuildStatus status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long repoId;

    public BuildDTO(Long id,
                    String sha,
                    String commitAuthor,
                    String commitMessage,
                    BuildStatus status,
                    LocalDateTime startTime,
                    LocalDateTime endTime,
                    Long repoId) {

        this.id = id;
        this.sha = sha;
        this.commitAuthor = commitAuthor;
        this.commitMessage = commitMessage;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repoId = repoId;
    }

    // getters only (immutable DTO)
    public Long getId() { return id; }
    public String getSha() { return sha; }
    public String getCommitAuthor() { return commitAuthor; }
    public String getCommitMessage() { return commitMessage; }
    public BuildStatus getStatus() { return status; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Long getRepoId() { return repoId; }
}
