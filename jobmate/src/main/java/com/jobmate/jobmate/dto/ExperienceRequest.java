package com.jobmate.jobmate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequest {
    @NotBlank
    @Size(max = 255)
    private String company;

    @NotBlank
    @Size(max = 255)
    private String designation;

    @NotBlank
    @Size(max = 100)
    private String employmentType;

    @Size(max = 255)
    private String location;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Start date must use yyyy-MM-dd format")
    private String startDate;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "End date must use yyyy-MM-dd format")
    private String endDate;

    @NotNull
    private Boolean currentlyWorking;

    @Size(max = 100)
    private List<@NotBlank @Size(max = 2000) String> highlights;

    @Size(max = 2000)
    private String achievements;

    @Size(max = 100)
    private List<@NotBlank @Size(max = 255) String> technologiesUsed;
}
