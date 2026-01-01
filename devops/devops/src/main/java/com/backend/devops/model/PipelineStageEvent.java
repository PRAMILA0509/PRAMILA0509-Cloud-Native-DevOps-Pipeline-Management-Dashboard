package com.backend.devops.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "pipeline_stage_events",
        indexes = {
                @Index(name = "idx_build_stage", columnList = "build_id, stage"),
                @Index(name = "idx_stage_status", columnList = "stage, status")
        }
)
public class PipelineStageEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_id", nullable = false)
    private Build build;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StageName stage;

    private String environment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StageStatus status;

    @Column(nullable = false)
    private LocalDateTime eventTime;

    public PipelineStageEvent() {}

    public PipelineStageEvent(
            Build build,
            StageName stage,
            String environment,
            StageStatus status,
            LocalDateTime eventTime
    ) {
        this.build = build;
        this.stage = stage;
        this.environment = environment;
        this.status = status;
        this.eventTime = eventTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public StageName getStage() {
        return stage;
    }

    public void setStage(StageName stage) {
        this.stage = stage;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public StageStatus getStatus() {
        return status;
    }

    public void setStatus(StageStatus status) {
        this.status = status;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}
