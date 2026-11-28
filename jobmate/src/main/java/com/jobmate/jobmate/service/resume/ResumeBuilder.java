package com.jobmate.jobmate.service.resume;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobmate.jobmate.dto.job.ResumeDraftDto;
import com.jobmate.jobmate.dto.job.UserResumeDto;
import com.jobmate.jobmate.exception.JobNotFoundException;
import com.jobmate.jobmate.model.job.Jobs;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.JobRepository;
import com.jobmate.jobmate.repository.UserProfileRepository;
import java.util.Map;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResumeBuilder {

    private final UserProfileRepository userProfileRepository;
    private final JobRepository jobRepository;
    private final ResumeAiService resumeAiService;
    private final ResumePdfService resumePdfService;
    private final CloudinaryService cloudinaryService;

    public ResumeBuilder(
            UserProfileRepository userProfileRepository,
            JobRepository jobRepository,
            ResumeAiService resumeAiService,
            ResumePdfService resumePdfService,
            CloudinaryService cloudinaryService
    ){
        this.userProfileRepository = userProfileRepository;
        this.jobRepository = jobRepository;
        this.resumeAiService = resumeAiService;
        this.resumePdfService = resumePdfService;
        this.cloudinaryService = cloudinaryService;
    }

    public String buildJobRelevantResume(Long id, String username){
        Jobs job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found"));
        UserProfile userProfile = userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        UserResumeDto user = new UserResumeDto(
                userProfile.getUser(),
                userProfile.getPersonalInfo(),
                userProfile.getAdditionalInfo(),
                userProfile.getExperiences(),
                userProfile.getSkills(),
                userProfile.getEducation(),
                userProfile.getProjects()
        );

        try{
            ResumeDraftDto resume = resumeAiService.generateResume(user, job);
            byte[] pdf = resumePdfService.generatePdf(resume);
            Map<?, ?> uploadResult = cloudinaryService.uploadPdf(pdf);
            if (uploadResult == null) {
                throw new IllegalStateException("Cloudinary upload returned no result");
            }
            Object secureUrl = uploadResult.get("secure_url");
            if (!(secureUrl instanceof String url) || url.isBlank()) {
                throw new IllegalStateException("Cloudinary upload did not return a secure URL");
            }
            return url;
        } catch(JsonProcessingException ex){
            throw new RuntimeException("Failed to process resume JSON", ex);
        }
    }
}
