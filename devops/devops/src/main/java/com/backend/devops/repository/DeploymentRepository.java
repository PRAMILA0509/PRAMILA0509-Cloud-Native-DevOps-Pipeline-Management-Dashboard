package com.backend.devops.repository;

import com.backend.devops.model.Build;
import com.backend.devops.model.Deployment;
import com.backend.devops.model.Environment;
import com.backend.devops.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeploymentRepository extends JpaRepository<Deployment, Long> {


    boolean existsByBuildAndEnvironment(Build build, Environment environment);


    Optional<Deployment> findTopByRepoAndEnvironmentOrderByStartedAtDesc(Repo repo, Environment environment);


    List<Deployment> findByBuild(Build build);


    List<Deployment> findByRepoAndEnvironmentOrderByStartedAtDesc(Repo repo, Environment environment);
}
