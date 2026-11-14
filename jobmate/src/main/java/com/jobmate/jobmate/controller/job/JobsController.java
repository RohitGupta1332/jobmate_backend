package com.jobmate.jobmate.controller.job;

import com.jobmate.jobmate.dto.job.JobListDto;
import com.jobmate.jobmate.dto.job.JobRequest;
import com.jobmate.jobmate.service.job.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrape")
public class JobsController {

    private final JobService jobService;

    public JobsController(JobService jobService){
        this.jobService = jobService;
    }
    @GetMapping
    public ResponseEntity<JobListDto> fetchJobs(@Valid @RequestBody JobRequest jobRequest){
        JobListDto jobResult = jobService.fetchJobs(jobRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jobResult);

    }
}
