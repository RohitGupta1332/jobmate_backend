package com.jobmate.jobmate.controller;

import com.jobmate.jobmate.dto.PersonalInfoRequest;
import com.jobmate.jobmate.dto.PersonalInfoResponse;
import com.jobmate.jobmate.service.PersonalInfoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/personal-info")
public class PersonalInfoController {
    private final PersonalInfoService personalInfoService;

    public PersonalInfoController(PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    @GetMapping
    public ResponseEntity<PersonalInfoResponse> find(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(personalInfoService.find(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<PersonalInfoResponse> save(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PersonalInfoRequest request) {
        return ResponseEntity.ok(personalInfoService.save(userDetails.getUsername(), request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails) {
        personalInfoService.delete(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
