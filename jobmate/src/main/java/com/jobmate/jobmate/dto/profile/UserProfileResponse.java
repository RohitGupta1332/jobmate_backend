package com.jobmate.jobmate.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private PersonalInfoResponse personalInfo;
    private AdditionalInfoResponse additionalInfo;
    private JobPreferencesResponse jobPreferences;
    private List<ExperienceResponse> experiences;
    private List<SkillsResponse> skills;
    private List<EducationResponse> education;
    private List<ProjectResponse> projects;
}
