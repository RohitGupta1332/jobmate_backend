package com.jobmate.jobmate.dto.profile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoRequest {
    @DecimalMin(value = "0.0", message = "Years of experience cannot be negative")
    private Double yearsOfExperience;

    @PositiveOrZero(message = "Current CTC cannot be negative")
    private Double currentCTC;

    @PositiveOrZero(message = "Expected CTC cannot be negative")
    private Double expectedCTC;

    @PositiveOrZero(message = "Notice period cannot be negative")
    private Integer noticePeriod;

    @Size(max = 100)
    private List<@NotBlank @Size(max = 255) String> certifications;
}
