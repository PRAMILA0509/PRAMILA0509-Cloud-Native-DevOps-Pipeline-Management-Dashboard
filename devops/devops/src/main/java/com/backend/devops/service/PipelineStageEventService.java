package com.backend.devops.service;

import com.backend.devops.model.PipelineStageEvent;
import com.backend.devops.model.Build;
import com.backend.devops.model.StageName;
import com.backend.devops.repository.PipelineStageEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineStageEventService {

    private final PipelineStageEventRepository eventRepository;

    public PipelineStageEventService(PipelineStageEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Save new stage event
    public PipelineStageEvent addEvent(PipelineStageEvent event) {
        return eventRepository.save(event);
    }

    // Get all events for a build
    public List<PipelineStageEvent> getEventsForBuild(Build build) {
        return eventRepository.findByBuildOrderByEventTimeAsc(build);
    }

    // Get events filtered by stage
    public List<PipelineStageEvent> getEventsForBuildByStage(Build build, StageName stage) {
        return eventRepository.findByBuildAndStageOrderByEventTimeAsc(build, stage);
    }

    // Get events filtered by environment
    public List<PipelineStageEvent> getEventsForBuildByEnvironment(Build build, String environment) {
        return eventRepository.findByBuildAndEnvironmentOrderByEventTimeAsc(build, environment);
    }
}
