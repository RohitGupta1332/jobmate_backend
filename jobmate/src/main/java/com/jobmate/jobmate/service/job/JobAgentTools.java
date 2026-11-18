package com.jobmate.jobmate.service.job;

import com.jobmate.jobmate.dto.job.JobRequest;
import com.jobmate.jobmate.dto.job.JobResponseDto;
import com.jobmate.jobmate.dto.job.JobScrapResultDto;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class JobAgentTools {
    private final JobDeduplicationService jobDeduplicationService;
    private final ExperienceFilterService experienceFilterService;
    private final RestClient restClient;

    public JobAgentTools(
            JobDeduplicationService jobDeduplicationService,
            ExperienceFilterService experienceFilterService,
            RestClient.Builder builder,
            @Value("${scraper.service.base-url}") String scrapUrl
    ){
        this.jobDeduplicationService = jobDeduplicationService;
        this.experienceFilterService = experienceFilterService;
        this.restClient = builder
                .baseUrl(scrapUrl)
                .build();
    }

    @Tool(description = """
    Scrape job listings from supported job portals using the provided
    job title and search parameters.

    Use this tool when additional job listings are needed.
    It returns scraped jobs that can subsequently be passed to
    duplicate removal, experience filtering, and job matching tools.
    """)
    public List<JobResponseDto> ScrapJobs(JobRequest request) {
        JobScrapResultDto jobScrapResultDto = restClient.post()
                .uri("/api/scrape")
                .body(request)
                .retrieve()
                .body(JobScrapResultDto.class);

        return jobScrapResultDto.getJobs();
    }

    @Tool(description = "Remove duplicate jobs from the provided job list")
    public List<JobResponseDto> removeDuplicates(List<JobResponseDto> jobs){
        return jobDeduplicationService.filterDuplicateJobs(jobs);
    }

    @Tool(description = "Filter jobs based on maximum required experience")
    public List<JobResponseDto> filterByExperience(List<JobResponseDto> jobs){
        List<JobResponseDto> relevantJobs = jobs
                .stream()
                .filter(job -> experienceFilterService
                        .isExperienceRelevant(job, 2))
                .toList();
        return relevantJobs;
    }


}
