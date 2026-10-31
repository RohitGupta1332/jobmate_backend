package com.jobmate.jobmate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPreferencesResponse {
    private Long id;
    private String jobTitle;
    private List<String> preferredLocations;
    private String employmentType;
    private Integer noticePeriod;
    private boolean relocate;
    private int preferredExperienceLevel;
}
