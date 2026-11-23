package com.jobmate.jobmate.controller.job;

import com.jobmate.jobmate.dto.job.JobMatchingResponseDto;
import com.jobmate.jobmate.dto.job.JobRequest;
import com.jobmate.jobmate.service.job.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobsController {

    private final JobService jobService;

    public JobsController(JobService jobService){
        this.jobService = jobService;
    }
    @PostMapping
    public ResponseEntity<JobMatchingResponseDto> scrapeFreshJobs(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody JobRequest jobRequest
    ){
        String username = userDetails.getUsername();
        JobMatchingResponseDto jobResult = jobService.findRelevantJobs(jobRequest, username);
        jobService.saveJobs(username, jobResult);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jobResult);

    }

    @GetMapping
    public ResponseEntity<JobMatchingResponseDto> fetchJobs(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        JobMatchingResponseDto jobs = jobService.fetchJobs(username);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jobs);
    }
}
