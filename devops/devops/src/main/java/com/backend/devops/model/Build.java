package com.backend.devops.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "builds")
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "repo_id", nullable = false)
    private Repo repo;

    private String sha;
    private String status; // SUCCESS / FAILED / PENDING
    private String message;
    private LocalDateTime timestamp;

    public Build() {}

    public Build(Repo repo, String sha, String status, String message, LocalDateTime timestamp) {
        this.repo = repo;
        this.sha = sha;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
