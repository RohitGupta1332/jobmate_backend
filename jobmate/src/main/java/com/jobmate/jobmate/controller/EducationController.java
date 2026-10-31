package com.jobmate.jobmate.controller;

import com.jobmate.jobmate.dto.EducationRequest;
import com.jobmate.jobmate.dto.EducationResponse;
import com.jobmate.jobmate.service.EducationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
public class EducationController {
    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    public ResponseEntity<EducationResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(educationService.create(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> findAll(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(educationService.findAll(userDetails.getUsername()));
    }

    @GetMapping("/{educationId}")
    public ResponseEntity<EducationResponse> findById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long educationId) {
        return ResponseEntity.ok(
                educationService.findById(userDetails.getUsername(), educationId));
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long educationId,
            @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(
                educationService.update(userDetails.getUsername(), educationId, request));
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long educationId) {
        educationService.delete(userDetails.getUsername(), educationId);
        return ResponseEntity.noContent().build();
    }
}
