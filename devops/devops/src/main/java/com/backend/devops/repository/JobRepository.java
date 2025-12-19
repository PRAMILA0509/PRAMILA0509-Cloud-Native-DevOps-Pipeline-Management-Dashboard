package com.backend.devops.repository;

import com.backend.devops.model.Build;
import com.backend.devops.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // Get all jobs for a specific build
    List<Job> findByBuild(Build build);
}
