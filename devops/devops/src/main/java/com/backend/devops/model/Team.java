package com.backend.devops.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Team is identified by its lead
    @OneToOne
    @JoinColumn(name = "team_lead_id", nullable = false, unique = true)
    private User teamLead;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Team() {}

    public Long getId() {
        return id;
    }

    public User getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(User teamLead) {
        this.teamLead = teamLead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
