package com.jobmate.jobmate.controller.auth;

import com.jobmate.jobmate.dto.auth.AuthRequest;
import com.jobmate.jobmate.dto.auth.LoginResponse;
import com.jobmate.jobmate.dto.auth.RegisterResponse;
import com.jobmate.jobmate.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(
            @Valid @RequestBody AuthRequest request) {
        RegisterResponse registration = authService.registerUser(request);
        AuthService.AuthenticatedSession session = authService.loginUser(request);
        registration.setAccessToken(session.accessToken());
        registration.setExpiresInSeconds(session.expiresInSeconds());
        return ResponseEntity.status(HttpStatus.CREATED)
                .cacheControl(CacheControl.noStore())
                .body(registration);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody AuthRequest request) {
        AuthService.AuthenticatedSession session = authService.loginUser(request);
        LoginResponse response = new LoginResponse(
                "Bearer", session.accessToken(), session.expiresInSeconds());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .cacheControl(CacheControl.noStore())
                .body(response);
    }
}
