package com.backend.devops.websocket;

import com.backend.devops.model.BuildStatus;
import java.time.LocalDateTime;

public record BuildDTO(
        Long id,
        String sha,
        BuildStatus status,
        String message,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long repoId
) {}
