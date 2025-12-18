package com.backend.devops.service;

import com.backend.devops.model.Build;
import com.backend.devops.model.Repo;
import com.backend.devops.repository.BuildRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildService {

    private final BuildRepository buildRepository;

    public BuildService(BuildRepository buildRepository) {
        this.buildRepository = buildRepository;
    }

    public Build saveBuild(Build build) {
        return buildRepository.save(build);
    }

    public List<Build> getLastBuilds(Repo repo) {
        return buildRepository.findTop10ByRepoOrderByTimestampDesc(repo);
    }


}
