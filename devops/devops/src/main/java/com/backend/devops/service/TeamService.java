package com.backend.devops.service;

import com.backend.devops.model.Team;
import com.backend.devops.model.User;
import com.backend.devops.repository.TeamRepository;
import com.backend.devops.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository,
                       UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }


    public Team getOrCreateTeamForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTeam() != null) {
            System.out.println("User already belongs to team: " + user.getTeam().getId());
            return user.getTeam();
        }


        Team team = new Team();
        team.setTeamLead(user);

        Team savedTeam = teamRepository.save(team);


        user.setTeam(savedTeam);
        userRepository.save(user);

        System.out.println("Created new team " + savedTeam.getId() + " with lead: " + user.getName());
        return savedTeam;
    }


    public void addUserToTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (user.getTeam() != null) {
            throw new RuntimeException("User already belongs to a team");
        }

        user.setTeam(team);
        userRepository.save(user);

        System.out.println("Added user " + user.getName() + " to team " + team.getId());
    }

    public Team getOrCreateSystemTeam() {
        return teamRepository.findById(1L).orElseGet(() -> {

            User systemUser = userRepository.findByEmail("system@devops.local")
                    .orElseGet(() -> {
                        User u = new User();
                        u.setName("SYSTEM");
                        u.setEmail("system@devops.local");
                        u.setPassword("SYSTEM");
                        u.setRole("SYSTEM");
                        return userRepository.save(u);
                    });

            Team team = new Team();
            team.setTeamLead(systemUser);
            return teamRepository.save(team);
        });
    }

}
