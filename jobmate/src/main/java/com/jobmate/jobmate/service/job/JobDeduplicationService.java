package com.jobmate.jobmate.service.job;

import com.jobmate.jobmate.dto.job.JobResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JobDeduplicationService {

    public List<JobResponseDto> filterDuplicateJobs(List<JobResponseDto> jobScrapResultDto) {
        List<JobResponseDto> uniqueJobs = new ArrayList<>();
        Set<String> seenJobKeys = new HashSet<>();

        if (jobScrapResultDto == null) {
            return uniqueJobs;
        }

        for (JobResponseDto scrapJob : jobScrapResultDto) {
            if (scrapJob == null) continue;

            String key = buildJobKey(scrapJob);

            // Set.add() returns true only if the key was not already present
            if (seenJobKeys.add(key)) {
                uniqueJobs.add(scrapJob);
            }
        }

        return uniqueJobs;
    }

    private String buildJobKey(JobResponseDto job) {
        String company = normalize(job.getCompany());
        String title = normalize(job.getTitle());
        String location = normalize(job.getLocation());
        return company + "|" + title + "|" + location;
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}