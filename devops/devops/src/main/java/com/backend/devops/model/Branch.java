package com.backend.devops.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "repo_branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "repo_id", nullable = false)
    @JsonBackReference // Prevents infinite recursion during JSON serialization
    private Repo repo;

    public Branch() {}

    public Branch(String name, Repo repo) {
        this.name = name;
        this.repo = repo;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Repo getRepo() { return repo; }
    public void setRepo(Repo repo) { this.repo = repo; }
}