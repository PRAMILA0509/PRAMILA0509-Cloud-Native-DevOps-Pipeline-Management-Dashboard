package com.backend.devops.repository;

import com.backend.devops.model.Build;
import com.backend.devops.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildRepository extends JpaRepository<Build, Long> {

    // Get builds for a repo, ordered by timestamp descending (latest first)
    List<Build> findByRepoOrderByStartTimeDesc(Repo repo);

}
