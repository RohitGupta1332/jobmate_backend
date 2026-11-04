package com.jobmate.jobmate.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotBlank(message = "username cannot be empty")
    private String username;

    @NotBlank(message = "password cannot be blank")
    private String password;
}
