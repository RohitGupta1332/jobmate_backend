package com.jobmate.jobmate.dto.job;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

    @NotBlank(message = "Title cannot be empty")
    private String jobTitle;

    @NotNull(message = "Provide number of results wanted")
    @Min(value = 1, message = "Results wanted must be at least 1")
    private Integer resultsWanted;

    @NotNull(message = "Provide duration of job posted")
    @Min(value = 1, message = "Hours old must be at least 1")
    private Integer hoursOld;
}
