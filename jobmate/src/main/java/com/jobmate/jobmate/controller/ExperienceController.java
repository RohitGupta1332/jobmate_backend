package com.jobmate.jobmate.controller;

import com.jobmate.jobmate.dto.ExperienceRequest;
import com.jobmate.jobmate.dto.ExperienceResponse;
import com.jobmate.jobmate.service.ExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {
    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<ExperienceResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(experienceService.create(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> findAll(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(experienceService.findAll(userDetails.getUsername()));
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> findById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long experienceId) {
        return ResponseEntity.ok(
                experienceService.findById(userDetails.getUsername(), experienceId));
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long experienceId,
            @Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.ok(
                experienceService.update(userDetails.getUsername(), experienceId, request));
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long experienceId) {
        experienceService.delete(userDetails.getUsername(), experienceId);
        return ResponseEntity.noContent().build();
    }
}
