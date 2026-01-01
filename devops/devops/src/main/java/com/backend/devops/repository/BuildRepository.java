package com.backend.devops.repository;


import com.backend.devops.model.Build;
import com.backend.devops.model.Repo;
import com.backend.devops.model.BuildStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildRepository extends JpaRepository<Build, Long> {
    List<Build> findByRepoOrderByStartTimeDesc(Repo repo);
    Optional<Build> findTopByRepoOrderByStartTimeDesc(Repo repo);
    Optional<Build> findTopByBranchAndStatusOrderByStartTimeDesc(String branch, BuildStatus status);

}
