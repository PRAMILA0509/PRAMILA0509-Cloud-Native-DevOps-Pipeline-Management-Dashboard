package com.backend.devops.repository;

import com.backend.devops.model.Build;
import com.backend.devops.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildRepository extends JpaRepository<Build, Long> {

    List<Build> findByRepoOrderByStartTimeDesc(Repo repo);
}
