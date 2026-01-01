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
    private final RepoService repoService;

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

        Build savedBuild = buildRepository.save(build);


        eventPublisher.publishBuildUpdate(savedBuild);

        return savedBuild;
    }


    public List<Build> getBuildsForRepo(Long repoId) {
        Repo repo = repoService.getRepoById(repoId);
        return buildRepository.findByRepoOrderByStartTimeDesc(repo);
    }


    public Build getLatestBuildForRepo(Long repoId) {
        Repo repo = repoService.getRepoById(repoId);

        return buildRepository
                .findTopByRepoOrderByStartTimeDesc(repo)
                .orElse(null);
    }


    public Build getBuildById(Long buildId) {
        return buildRepository.findById(buildId)
                .orElseThrow(() -> new RuntimeException("Build not found with id: " + buildId));
    }


    public Build updateBuildStatus(Long buildId, BuildStatus status) {
        Build build = buildRepository.findById(buildId)
                .orElseThrow(() -> new RuntimeException("Build not found"));

        build.setStatus(status);

        if (status != BuildStatus.RUNNING) {
            build.setEndTime(LocalDateTime.now());
        }

        return saveBuild(build);
    }

    public Long getLatestBuildIdForBranch(String branch) {
        Build build = buildRepository
                .findTopByBranchAndStatusOrderByStartTimeDesc(branch, BuildStatus.SUCCESS)
                .orElseThrow(() -> new RuntimeException("No successful build found for branch: " + branch));
        return build.getId();
    }


}
