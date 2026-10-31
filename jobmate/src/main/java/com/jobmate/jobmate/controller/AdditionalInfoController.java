package com.jobmate.jobmate.controller;

import com.jobmate.jobmate.dto.AdditionalInfoRequest;
import com.jobmate.jobmate.dto.AdditionalInfoResponse;
import com.jobmate.jobmate.service.AdditionalInfoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/additional-info")
public class AdditionalInfoController {
    private final AdditionalInfoService additionalInfoService;

    public AdditionalInfoController(AdditionalInfoService additionalInfoService) {
        this.additionalInfoService = additionalInfoService;
    }

    @GetMapping
    public ResponseEntity<AdditionalInfoResponse> find(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(additionalInfoService.find(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<AdditionalInfoResponse> save(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AdditionalInfoRequest request) {
        return ResponseEntity.ok(additionalInfoService.save(userDetails.getUsername(), request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails) {
        additionalInfoService.delete(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
