package com.jobmate.jobmate.dto.job;

import lombok.Data;

import java.util.List;

@Data
public class JobScrapResultDto {

    private int totalJobs;

    private List<JobResponseDto> jobs;


}
