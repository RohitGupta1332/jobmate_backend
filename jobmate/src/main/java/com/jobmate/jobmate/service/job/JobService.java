package com.jobmate.jobmate.service.job;

import com.jobmate.jobmate.dto.job.JobListDto;
import com.jobmate.jobmate.dto.job.JobRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class JobService {
    private final RestClient restClient;

    public JobService(
            RestClient.Builder builder,
            @Value("${scraper.service.base-url}") String baseUrl
    ) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public JobListDto fetchJobs(JobRequest request){
        return restClient.post()
                .uri("/api/scrape")
                .body(request)
                .retrieve()
                .body(JobListDto.class);
    }


}
