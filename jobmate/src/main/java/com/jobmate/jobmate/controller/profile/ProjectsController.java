package com.jobmate.jobmate.controller.profile;

import com.jobmate.jobmate.dto.profile.ProjectRequest;
import com.jobmate.jobmate.dto.profile.ProjectResponse;
import com.jobmate.jobmate.service.profile.ProjectsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {
    private final ProjectsService projectsService;

    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectsService.create(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> findAll(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projectsService.findAll(userDetails.getUsername()));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> findById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(projectsService.findById(userDetails.getUsername(), projectId));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(
                projectsService.update(userDetails.getUsername(), projectId, request));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long projectId) {
        projectsService.delete(userDetails.getUsername(), projectId);
        return ResponseEntity.noContent().build();
    }
}
