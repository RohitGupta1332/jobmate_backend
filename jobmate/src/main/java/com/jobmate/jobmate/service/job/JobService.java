package com.jobmate.jobmate.service.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobmate.jobmate.dto.job.*;
import com.jobmate.jobmate.exception.FetchJobResponseException;
import com.jobmate.jobmate.exception.UserNotFoundException;
import com.jobmate.jobmate.model.job.Jobs;
import com.jobmate.jobmate.model.job.Status;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.JobRepository;
import com.jobmate.jobmate.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobMatchingAgent jobMatchingAgent;
    private final UserProfileRepository userProfileRepository;

    private final JobRepository jobRepository;
    private final JobDeduplicationService jobDeduplicationService;
    private final ExperienceFilterService experienceFilterService;
    private final RestClient restClient;

    public JobService(
            JobMatchingAgent jobMatchingAgent,
            UserProfileRepository userProfileRepository,
            JobRepository jobRepository,
            JobDeduplicationService jobDeduplicationService,
            ExperienceFilterService experienceFilterService,
            RestClient.Builder builder,
            @Value("${scraper.service.base-url}") String scrapUrl
    ){
        this.jobMatchingAgent = jobMatchingAgent;
        this.userProfileRepository = userProfileRepository;
        this.jobRepository = jobRepository;
        this.jobDeduplicationService = jobDeduplicationService;
        this.experienceFilterService = experienceFilterService;
        this.restClient = builder
                .baseUrl(scrapUrl)
                .build();
    }

    public JobMatchingResponseDto findRelevantJobs(JobRequest jobRequest, String username){
        UserProfile userProfile = userProfileRepository.findByUser_UsernameIgnoreCase(
                username
        ).orElseThrow(() -> new UserNotFoundException("Username not found"));
        UserProfileForAgentDto profile = new UserProfileForAgentDto(
                userProfile.getAdditionalInfo(),
                userProfile.getExperiences(),
                userProfile.getSkills(),
                userProfile.getEducation(),
                userProfile.getProjects()
        );
        List<JobResponseDto> jobs = scrapJobs(jobRequest, username);
        List<JobAnalysisDto> finalFilteredJobs = new ArrayList<>();
        int batchSize = 4;
        for(int i = 0; i < jobs.size(); i += batchSize){
            List<JobResponseDto> batchJobs = jobs.subList(i, Math.min(i+batchSize, jobs.size()));
            JobMatchingResponseDto jobMatchingResponseDto = jobMatchingAgent.analyzeJobs(batchJobs, profile);
            finalFilteredJobs.addAll(jobMatchingResponseDto.getResults());
        }
        JobMatchingResponseDto result = new JobMatchingResponseDto();
        result.setResults(finalFilteredJobs);
        return result;
    }

    public void saveJobs(String username, JobMatchingResponseDto jobMatchingResponseDto){
        if(jobMatchingResponseDto == null || jobMatchingResponseDto.getResults().isEmpty()){
            return;
        }
        List<JobAnalysisDto> jobs = jobMatchingResponseDto.getResults();
        Jobs job = new Jobs();
        List<Jobs> relevantJobs = new ArrayList<>();

        for(JobAnalysisDto analysisDto: jobs){
            job.setUsername(username);
            job.setTitle(analysisDto.getTitle());
            job.setCompany(analysisDto.getCompany());
            job.setUrl(analysisDto.getUrl());
            job.setLocation(analysisDto.getLocation());
            job.setJobId(analysisDto.getJobId());
            job.setDescription(analysisDto.getDescription());
            job.setApplicationStatus(Status.APPLY);

            relevantJobs.add(job);
        }
        jobRepository.saveAll(relevantJobs);

    }


    private List<JobResponseDto> scrapJobs(
            JobRequest request,
            String username
    ) {

        // 1. Call scraping API
        JobScrapResultDto jobScrapResultDto = restClient.post()
                .uri("/api/scrape")
                .body(request)
                .retrieve()
                .body(JobScrapResultDto.class);

        if (jobScrapResultDto == null ||
                jobScrapResultDto.getJobs() == null) {
            return new ArrayList<>();
        }

        // 2. Remove duplicates within API response
        List<JobResponseDto> uniqueJobs =
                jobDeduplicationService.filterDuplicateJobs(
                        jobScrapResultDto.getJobs()
                );

        // 3. Get all scraped job URLs
        List<String> jobUrls = uniqueJobs.stream()
                .map(JobResponseDto::getJobUrl)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // 4. Fetch jobs already present in database
        Set<String> existingJobUrls = jobRepository
                .findByUsernameAndUrlIn(username, jobUrls)
                .stream()
                .map(Jobs::getUrl)
                .collect(Collectors.toSet());

        // 5. Remove jobs already present in database
        List<JobResponseDto> newJobs = uniqueJobs.stream()
                .filter(job -> !existingJobUrls.contains(job.getJobUrl()))
                .toList();

        // 6. Apply experience filter
        return newJobs.stream()
                .filter(job -> experienceFilterService
                        .isExperienceRelevant(job, 2))
                .toList();
    }


    public JobMatchingResponseDto fetchJobs(String username) {
        List<Jobs> jobs = jobRepository.findByUsername(username);
        List<JobAnalysisDto> result = new ArrayList<>();
        for(Jobs job: jobs){
            JobAnalysisDto analysisDto = new JobAnalysisDto();
            analysisDto.setTitle(job.getTitle());
            analysisDto.setUrl(job.getUrl());
            analysisDto.setLocation(job.getLocation());
            analysisDto.setDescription(job.getDescription());
            analysisDto.setCompany(job.getCompany());
            analysisDto.setApplicationStatus(job.getApplicationStatus());

            result.add(analysisDto);

        }
        JobMatchingResponseDto response = new JobMatchingResponseDto();
        response.setResults(result);
        return response;
    }
}



