package com.jobmate.jobmate.controller;

import com.jobmate.jobmate.dto.AuthRequest;
import com.jobmate.jobmate.dto.LoginResponse;
import com.jobmate.jobmate.dto.RegisterResponse;
import com.jobmate.jobmate.service.AuthService;
import jakarta.validation.Valid;
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
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody AuthRequest request){
        RegisterResponse response = authService.registerUser(request);
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody AuthRequest request){
        LoginResponse response = authService.loginUser(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }
}
