package com.backend.devops.controller;

import com.backend.devops.model.PipelineStageEvent;
import com.backend.devops.model.Build;
import com.backend.devops.service.BuildService;
import com.backend.devops.service.PipelineStageEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.devops.model.StageName;
import java.util.List;

@RestController
@RequestMapping("/api/stage-events")
@CrossOrigin(origins = "http://localhost:3000")
public class PipelineStageEventController {

    private final PipelineStageEventService eventService;
    private final BuildService buildService;

    public PipelineStageEventController(PipelineStageEventService eventService, BuildService buildService) {
        this.eventService = eventService;
        this.buildService = buildService;
    }

    // Add new stage event
    @PostMapping("/add")
    public ResponseEntity<PipelineStageEvent> addStageEvent(@RequestBody PipelineStageEvent event) {
        return ResponseEntity.ok(eventService.addEvent(event));
    }

    // Get all events for a build
    @GetMapping("/build/{buildId}")
    public ResponseEntity<List<PipelineStageEvent>> getEventsForBuild(@PathVariable Long buildId) {
        Build build = buildService.getBuildById(buildId);
        return ResponseEntity.ok(eventService.getEventsForBuild(build));
    }

    // Optional: filter by stage
    @GetMapping("/build/{buildId}/stage/{stage}")
    public ResponseEntity<List<PipelineStageEvent>> getEventsForBuildByStage(
            @PathVariable Long buildId,
            @PathVariable String stage
    ) {
        Build build = buildService.getBuildById(buildId);
        return ResponseEntity.ok(eventService.getEventsForBuildByStage(build, StageName.valueOf(stage)));
    }

    // Optional: filter by environment
    @GetMapping("/build/{buildId}/env/{environment}")
    public ResponseEntity<List<PipelineStageEvent>> getEventsForBuildByEnvironment(
            @PathVariable Long buildId,
            @PathVariable String environment
    ) {
        Build build = buildService.getBuildById(buildId);
        return ResponseEntity.ok(eventService.getEventsForBuildByEnvironment(build, environment));
    }
}
