package com.jobmate.jobmate.service.profile;

import com.jobmate.jobmate.dto.profile.ProjectRequest;
import com.jobmate.jobmate.dto.profile.ProjectResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.profile.Projects;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectsService extends UserProfileService {
    public ProjectsService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        super(userRepository, userProfileRepository);
    }

    @Transactional
    public ProjectResponse create(String username, ProjectRequest request) {
        UserProfile profile = findOrCreateProfile(username);
        List<Projects> projects = ensureProjects(profile);
        Projects project = toEntity(request);
        projects.add(project);
        userProfileRepository.save(profile);
        return toResponse(project);
    }

    @Transactional
    public List<ProjectResponse> findAll(String username) {
        return userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getProjects)
                .orElseGet(List::of)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProjectResponse findById(String username, Long projectId) {
        return toResponse(findProject(username, projectId));
    }

    @Transactional
    public ProjectResponse update(String username, Long projectId, ProjectRequest request) {
        Projects project = findProject(username, projectId);
        updateEntity(project, request);
        return toResponse(project);
    }

    @Transactional
    public void delete(String username, Long projectId) {
        UserProfile profile = findProfile(username);
        boolean removed = ensureProjects(profile).removeIf(project -> projectId.equals(project.getId()));
        if (!removed) {
            throw projectNotFound(projectId);
        }
        userProfileRepository.save(profile);
    }

    private Projects findProject(String username, Long projectId) {
        return userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getProjects)
                .orElseGet(List::of)
                .stream()
                .filter(project -> projectId.equals(project.getId()))
                .findFirst()
                .orElseThrow(() -> projectNotFound(projectId));
    }

    private List<Projects> ensureProjects(UserProfile profile) {
        if (profile.getProjects() == null) {
            profile.setProjects(new ArrayList<>());
        }
        return profile.getProjects();
    }

    private ResourceNotFoundException projectNotFound(Long projectId) {
        return new ResourceNotFoundException(
                "Project with id " + projectId + " was not found for the current user");
    }

    private Projects toEntity(ProjectRequest request) {
        Projects project = new Projects();
        updateEntity(project, request);
        return project;
    }

    private void updateEntity(Projects project, ProjectRequest request) {
        project.setName(request.getName().trim());
        project.setDescription(request.getDescription().trim());
        project.setTechnologiesUsed(copy(request.getTechnologiesUsed()));
        project.setGithubUrl(request.getGithubUrl());
        project.setLiveUrl(request.getLiveUrl());
        project.setContributions(copy(request.getContributions()));
    }

    private List<String> copy(List<String> values) {
        return values == null ? null : new ArrayList<>(values);
    }

    private ProjectResponse toResponse(Projects project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTechnologiesUsed(),
                project.getGithubUrl(),
                project.getLiveUrl(),
                project.getContributions());
    }
}
