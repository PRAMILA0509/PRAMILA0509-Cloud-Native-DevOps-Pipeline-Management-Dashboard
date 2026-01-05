package com.backend.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repos")
public class Repo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String url;

    private String owner;

    // The main branch of the repository
    private String defaultBranch;

    // Optional: multiple branches if needed in future
    @ElementCollection
    @CollectionTable(name = "repo_branches", joinColumns = @JoinColumn(name = "repo_id"))
    @Column(name = "branch")
    private List<String> branches = new ArrayList<>();

    @OneToMany(mappedBy = "repo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Build> builds = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private LocalDateTime createdAt = LocalDateTime.now();



    public Repo() {}

    public Repo(Long id, String name, String url, String owner, String defaultBranch, Team team, LocalDateTime createdAt, List<Build> builds) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.owner = owner;
        this.defaultBranch = defaultBranch;
        this.team = team;
        this.createdAt = createdAt;
        this.builds = builds;
    }

    // ---------------- Getters & Setters ----------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public List<String> getBranches() {
        return branches;
    }

    public void setBranches(List<String> branches) {
        this.branches = branches;
    }

    public List<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
