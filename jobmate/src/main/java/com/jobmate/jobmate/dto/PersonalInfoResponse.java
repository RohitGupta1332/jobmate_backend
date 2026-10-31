package com.jobmate.jobmate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String linkedInUrl;
    private String githubUrl;
    private String portfolio;
    private String profileSummary;
}
