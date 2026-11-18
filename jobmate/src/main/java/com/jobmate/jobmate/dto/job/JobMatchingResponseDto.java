package com.jobmate.jobmate.dto.job;

import lombok.Data;

import java.util.List;

@Data
public class JobMatchingResponseDto {
    private List<JobAnalysisDto> results;
}
