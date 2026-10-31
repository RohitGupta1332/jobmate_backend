package com.jobmate.jobmate.controller;

import com.jobmate.jobmate.dto.JobPreferencesRequest;
import com.jobmate.jobmate.dto.JobPreferencesResponse;
import com.jobmate.jobmate.service.JobPreferencesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/job-preferences")
public class JobPreferencesController {
    private final JobPreferencesService jobPreferencesService;

    public JobPreferencesController(JobPreferencesService jobPreferencesService) {
        this.jobPreferencesService = jobPreferencesService;
    }

    @GetMapping
    public ResponseEntity<JobPreferencesResponse> find(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(jobPreferencesService.find(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<JobPreferencesResponse> save(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody JobPreferencesRequest request) {
        return ResponseEntity.ok(jobPreferencesService.save(userDetails.getUsername(), request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails) {
        jobPreferencesService.delete(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
