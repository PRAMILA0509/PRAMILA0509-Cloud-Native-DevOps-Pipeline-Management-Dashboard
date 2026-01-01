package com.backend.devops.repository;

import com.backend.devops.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // Find team by team lead user ID
    Optional<Team> findByTeamLeadId(Long teamLeadId);
}
