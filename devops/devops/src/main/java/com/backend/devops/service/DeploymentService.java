package com.backend.devops.service;

import com.backend.devops.model.*;
import com.backend.devops.repository.DeploymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeploymentService {

    private final DeploymentRepository deploymentRepository;

    public DeploymentService(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    public void autoDeployToDev(Repo repo, Build build) {
        boolean alreadyDeployed = deploymentRepository.existsByBuildAndEnvironment(build, Environment.DEV);
        if (alreadyDeployed) {
            System.out.println("⚠ DEV deployment already exists for this build");
            return;
        }

        Deployment deployment = new Deployment(
                repo,
                build,
                Environment.DEV,
                DeploymentStatus.DEPLOYING,
                LocalDateTime.now()
        );
        deploymentRepository.save(deployment);
    }

    public Deployment promote(Build build, Environment environment) {
        if (build.getStatus() != BuildStatus.SUCCESS) {
            throw new RuntimeException("Only SUCCESS builds can be promoted");
        }

        Deployment deployment = new Deployment(
                build.getRepo(),
                build,
                environment,
                DeploymentStatus.DEPLOYING,
                LocalDateTime.now()
        );

        return deploymentRepository.save(deployment);
    }

    public Deployment updateDeploymentStatus(Long deploymentId, DeploymentStatus status) {
        Deployment deployment = deploymentRepository.findById(deploymentId)
                .orElseThrow(() -> new RuntimeException("Deployment not found"));

        deployment.setStatus(status);
        deployment.setFinishedAt(LocalDateTime.now());

        return deploymentRepository.save(deployment);
    }

    public Deployment triggerDeployment(Repo repo, Build build, Environment environment) {
        return promote(build, environment);
    }

    public Optional<Deployment> getLatestDeployment(Repo repo, Environment environment) {
        return deploymentRepository.findTopByRepoAndEnvironmentOrderByStartedAtDesc(repo, environment);
    }

    public List<Deployment> getDeploymentsForBuild(Build build) {
        return deploymentRepository.findByBuild(build);
    }
}
