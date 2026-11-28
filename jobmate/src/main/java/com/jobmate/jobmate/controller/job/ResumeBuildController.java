package com.jobmate.jobmate.controller.job;

import com.jobmate.jobmate.dto.job.ResumeBuildResponse;
import com.jobmate.jobmate.dto.job.ResumeRequest;
import com.jobmate.jobmate.service.resume.ResumeBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/resume")
public class ResumeBuildController {
    private final ResumeBuilder resumeBuilder;

    public ResumeBuildController(ResumeBuilder resumeBuilder){
        this.resumeBuilder = resumeBuilder;
    }

    @PostMapping
    public ResponseEntity<ResumeBuildResponse> buildResume(@RequestBody ResumeRequest request, Authentication authentication) {
        String resumeUrl = resumeBuilder.buildJobRelevantResume(
                request.getJobId(),
                authentication.getName()
        );

        return ResponseEntity.ok(new ResumeBuildResponse(resumeUrl));
    }
}
