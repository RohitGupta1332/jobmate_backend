package com.jobmate.jobmate.dto;

import com.jobmate.jobmate.model.Proficiency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillsRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Proficiency proficiency;

    @PositiveOrZero(message = "Years of experience cannot be negative")
    private Double yearExperience;
}
