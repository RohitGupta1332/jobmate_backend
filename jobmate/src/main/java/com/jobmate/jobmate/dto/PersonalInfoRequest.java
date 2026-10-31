package com.jobmate.jobmate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoRequest {
    @NotBlank
    @Size(max = 255)
    private String fullName;

    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    @Pattern(regexp = "^[+()0-9 .-]{7,32}$", message = "Phone must be a valid phone number")
    private String phone;

    @Size(max = 255)
    private String location;

    @Size(max = 2048)
    @Pattern(regexp = "^https?://\\S+$", message = "LinkedIn URL must start with http:// or https://")
    private String linkedInUrl;

    @Size(max = 2048)
    @Pattern(regexp = "^https?://\\S+$", message = "GitHub URL must start with http:// or https://")
    private String githubUrl;

    @Size(max = 2048)
    @Pattern(regexp = "^https?://\\S+$", message = "Portfolio URL must start with http:// or https://")
    private String portfolio;

    @Size(max = 2000)
    private String profileSummary;
}
