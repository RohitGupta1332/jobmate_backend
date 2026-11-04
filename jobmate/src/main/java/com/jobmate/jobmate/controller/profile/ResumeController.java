package com.jobmate.jobmate.controller.profile;

import com.jobmate.jobmate.dto.profile.ResumeResponse;
import com.jobmate.jobmate.service.profile.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeResponse> upload(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resumeService.uploadResume(
                        userDetails.getUsername(), file));
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long resumeId) {

        resumeService.deleteResume(
                userDetails.getUsername(), resumeId);

        return ResponseEntity.noContent().build();
    }
}