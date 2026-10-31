package com.jobmate.jobmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPreferencesRequest {
    @NotBlank
    @Size(max = 255)
    private String jobTitle;

    @NotNull
    @Size(min = 1, max = 100)
    private List<@NotBlank @Size(max = 255) String> preferredLocations;

    @NotBlank
    @Size(max = 100)
    private String employmentType;

    @PositiveOrZero(message = "Notice period cannot be negative")
    private Integer noticePeriod;

    @NotNull
    private Boolean relocate;

    @NotNull
    @PositiveOrZero
    private Integer preferredExperienceLevel;
}
