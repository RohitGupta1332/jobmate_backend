package com.jobmate.jobmate.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {
    @Valid
    private PersonalInfoRequest personalInfo;

    @Valid
    private AdditionalInfoRequest additionalInfo;

    @Valid
    private JobPreferencesRequest jobPreferences;

    @Size(max = 100)
    private List<@NotNull @Valid ExperienceRequest> experiences;

    @Size(max = 100)
    private List<@NotNull @Valid SkillsRequest> skills;

    @Size(max = 100)
    private List<@NotNull @Valid EducationRequest> education;
 
    @Size(max = 100)
    private List<@NotNull @Valid ProjectRequest> projects;
}
