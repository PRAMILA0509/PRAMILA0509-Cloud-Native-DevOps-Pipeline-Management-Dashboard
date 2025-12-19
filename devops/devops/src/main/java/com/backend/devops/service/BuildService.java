package com.backend.devops.service;

import com.backend.devops.model.Build;
import com.backend.devops.model.BuildStatus;
import com.backend.devops.model.Repo;
import com.backend.devops.repository.BuildRepository;
import com.backend.devops.repository.RepoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BuildService {

    private final BuildRepository buildRepository;
    private final RepoRepository repoRepository;

    public BuildService(BuildRepository buildRepository, RepoRepository repoRepository) {
        this.buildRepository = buildRepository;
        this.repoRepository = repoRepository;
    }

    // Add build to repo
    public Build addBuildToRepo(Long repoId, Build build) {
        Repo repo = repoRepository.findById(repoId).orElse(null);
        if (repo == null) return null;

        build.setRepo(repo);

// If status is null, set default to PENDING
        if (build.getStatus() == null) {
            build.setStatus(BuildStatus.valueOf(BuildStatus.PENDING.name()));
        }

// If startTime is null, set it to now
        if (build.getStartTime() == null) {
            build.setStartTime(LocalDateTime.now());
        }

// endTime can be null initially, it will be set when the build finishes

        return buildRepository.save(build);
    }

    // Get all builds for repo
    public List<Build> getBuildsForRepo(Long repoId) {
        Repo repo = repoRepository.findById(repoId).orElse(null);
        if (repo == null) return List.of();
        return buildRepository.findByRepoOrderByStartTimeDesc(repo);
    }

    // Save build from webhook
    public Build saveBuild(Build build) {
        if (build.getStartTime() == null) build.setStartTime(LocalDateTime.now());
        return buildRepository.save(build);
    }

}
