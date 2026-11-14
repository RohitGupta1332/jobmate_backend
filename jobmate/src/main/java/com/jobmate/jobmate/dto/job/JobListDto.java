package com.jobmate.jobmate.dto.job;

import lombok.Data;

import java.util.List;

@Data
public class JobListDto {
    private List<JobResponseDto> jobs;
}
