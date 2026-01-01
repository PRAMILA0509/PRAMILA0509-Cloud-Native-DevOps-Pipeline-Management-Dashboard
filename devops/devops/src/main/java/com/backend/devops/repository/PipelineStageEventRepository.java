package com.backend.devops.repository;

import com.backend.devops.model.PipelineStageEvent;
import com.backend.devops.model.Build;
import com.backend.devops.model.StageName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineStageEventRepository extends JpaRepository<PipelineStageEvent, Long> {

    // Find all events for a build, ordered by time
    List<PipelineStageEvent> findByBuildOrderByEventTimeAsc(Build build);

    // Optional: filter by stage or environment
    List<PipelineStageEvent> findByBuildAndStageOrderByEventTimeAsc(Build build, StageName stage);
    List<PipelineStageEvent> findByBuildAndEnvironmentOrderByEventTimeAsc(Build build, String environment);
}
