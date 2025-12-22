package com.backend.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "builds")
public class Build{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repo_id")
    @JsonIgnore
    private Repo repo;

    @Setter
    private String sha;

    @Setter
    @Enumerated(EnumType.STRING)
    private BuildStatus status;

    @Setter
    private String message;

    @Setter
    private LocalDateTime startTime;
    @Setter
    private LocalDateTime endTime;

    // ✅ REQUIRED by JPA
    public Build() {
    }

    // ✅ Constructor for creating new builds (API / webhook)
    public Build(Repo repo,
                 String sha,
                 BuildStatus status,
                 String message,
                 LocalDateTime startTime) {

        this.repo = repo;
        this.sha = sha;
        this.status = status;
        this.message = message;
        this.startTime = startTime;
    }

    // -------- Getters & Setters --------

    public Long getId() {
        return id;
    }

    public Repo getRepo() {
        return repo;
    }

    public String getSha() {
        return sha;
    }

    public BuildStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }
}
