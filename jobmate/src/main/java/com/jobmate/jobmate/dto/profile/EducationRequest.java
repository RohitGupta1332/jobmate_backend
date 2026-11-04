package com.jobmate.jobmate.dto.profile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {
    @NotBlank
    @Size(max = 255)
    private String degree;

    @Size(max = 255)
    private String specialization;

    @NotBlank
    @Size(max = 255)
    private String college;

    @NotNull
    @Positive
    private String location;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer startYear;

    @Pattern(regexp = "^\\d{4}$", message = "End year must be a four-digit year")
    private String endYear;

    @NotNull
    @PositiveOrZero
    private Double cgpa;

    @NotNull
    private Boolean pursuing;
}
