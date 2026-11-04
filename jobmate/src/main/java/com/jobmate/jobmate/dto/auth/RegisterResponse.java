package com.jobmate.jobmate.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String username;
    private String message;
    private String accessToken;
    private long expiresInSeconds;
}
