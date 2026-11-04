package com.jobmate.jobmate.controller.profile;

import com.jobmate.jobmate.dto.profile.SkillsRequest;
import com.jobmate.jobmate.dto.profile.SkillsResponse;
import com.jobmate.jobmate.service.profile.SkillsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {
    private final SkillsService skillsService;

    public SkillsController(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    @PostMapping
    public ResponseEntity<SkillsResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody SkillsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skillsService.create(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<SkillsResponse>> findAll(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(skillsService.findAll(userDetails.getUsername()));
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillsResponse> findById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long skillId) {
        return ResponseEntity.ok(skillsService.findById(userDetails.getUsername(), skillId));
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillsResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long skillId,
            @Valid @RequestBody SkillsRequest request) {
        return ResponseEntity.ok(
                skillsService.update(userDetails.getUsername(), skillId, request));
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long skillId) {
        skillsService.delete(userDetails.getUsername(), skillId);
        return ResponseEntity.noContent().build();
    }
}
