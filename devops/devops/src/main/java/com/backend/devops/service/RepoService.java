package com.backend.devops.service;

import com.backend.devops.model.Repo;
import com.backend.devops.model.Team;
import com.backend.devops.model.User;
import com.backend.devops.repository.RepoRepository;
import com.backend.devops.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RepoService {

    private final RepoRepository repoRepository;
    private final UserRepository userRepository;
    private final TeamService teamService;

    public RepoService(RepoRepository repoRepository,
                       UserRepository userRepository,
                       TeamService teamService) {
        this.repoRepository = repoRepository;
        this.userRepository = userRepository;
        this.teamService = teamService;
    }


    public Repo registerRepoForUser(Long userId,
                                    String name,
                                    String url,
                                    String owner,
                                    String defaultBranch) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team team = teamService.getOrCreateTeamForUser(userId);

        return repoRepository.findByName(name)
                .orElseGet(() -> {
                    Repo repo = new Repo();
                    repo.setName(name);
                    repo.setUrl(url);
                    repo.setOwner(owner);               // GitHub owner
                    repo.setDefaultBranch(defaultBranch);
                    repo.setTeam(team);                 // 🔥 REQUIRED
                    return repoRepository.save(repo);
                });
    }

    public Repo findRepoByNameOrNull(String name) {
        return repoRepository.findByName(name).orElse(null);
    }


    public Repo getRepoById(Long id) {
        return repoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Repo not found with id: " + id));
    }

    public List<Repo> getAllRepos() {
        return repoRepository.findAll();
    }

    public Long getRepoIdByBranch(String branch) {
        // UPDATED: Now calls findByBranches_Name to match the Repository change
        Repo repo = repoRepository.findByBranches_Name(branch)
                .orElseThrow(() -> new RuntimeException("No repo found for branch: " + branch));
        return repo.getId();
    }
}
