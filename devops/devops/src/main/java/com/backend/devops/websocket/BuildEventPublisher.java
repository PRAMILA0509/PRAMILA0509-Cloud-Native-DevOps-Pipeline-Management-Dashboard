package com.backend.devops.websocket;

import com.backend.devops.model.Build;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BuildEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public BuildEventPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publishBuildUpdate(Build build) {

        if (build == null) {
            throw new IllegalArgumentException("Build cannot be null");
        }
        if (build.getRepo() == null || build.getRepo().getId() == null) {
            throw new IllegalStateException("Build must be associated with a Repo");
        }

        BuildDTO dto = new BuildDTO(
                build.getId(),
                build.getSha(),
                build.getCommitAuthor(),
                build.getCommitMessage(),
                build.getStatus(),
                build.getStartTime(),
                build.getEndTime(),
                build.getRepo().getId()
        );

        messagingTemplate.convertAndSend(
                "/topic/builds/" + build.getRepo().getId(),
                dto
        );
    }
}
