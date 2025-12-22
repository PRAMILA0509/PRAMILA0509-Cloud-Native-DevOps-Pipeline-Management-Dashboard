package com.backend.devops.service;

import com.backend.devops.model.Build;
import com.backend.devops.model.BuildStatus;
import com.backend.devops.model.Repo;
import com.backend.devops.repository.BuildRepository;
import com.backend.devops.websocket.BuildEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BuildService {

    private final BuildRepository buildRepository;
    private final BuildEventPublisher eventPublisher;
    private final RepoService repoService; // inject RepoService

    public BuildService(BuildRepository buildRepository,
                        BuildEventPublisher eventPublisher,
                        RepoService repoService) {
        this.buildRepository = buildRepository;
        this.eventPublisher = eventPublisher;
        this.repoService = repoService;
    }

    public Build saveBuild(Build build) {
        if (build.getStartTime() == null) {
            build.setStartTime(LocalDateTime.now());
        }

        Build saved = buildRepository.save(build);

        // 🔥 Publish real-time WebSocket update
        eventPublisher.publishBuildUpdate(saved);

        return saved;
    }


    public List<Build> getBuildsByRepo(Repo repo) {
        return buildRepository.findByRepoOrderByStartTimeDesc(repo);
    }

    // Add a build to a repository by repoId
    public Build addBuildToRepo(Long repoId, Build build) {
        Repo repo = repoService.getRepoById(repoId);
        if (repo == null) {
            return null; // repo not found
        }

        build.setRepo(repo);
        build.setStatus(BuildStatus.PENDING); // default status
        return saveBuild(build);
    }

    // Get builds for a repository by repoId
    public List<Build> getBuildsForRepo(Long repoId) {
        Repo repo = repoService.getRepoById(repoId);
        if (repo == null) {
            return List.of(); // return empty list if repo not found
        }
        return getBuildsByRepo(repo);
    }
}
