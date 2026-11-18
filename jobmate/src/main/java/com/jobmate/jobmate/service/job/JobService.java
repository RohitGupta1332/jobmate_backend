package com.jobmate.jobmate.service.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobmate.jobmate.dto.job.*;
import com.jobmate.jobmate.exception.FetchJobResponseException;
import com.jobmate.jobmate.exception.UserNotFoundException;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class JobService {
    private final JobMatchingAgent jobMatchingAgent;
    private final UserProfileRepository userProfileRepository;

    public JobService(
            JobMatchingAgent jobMatchingAgent,
            UserProfileRepository userProfileRepository
    ){
        this.jobMatchingAgent = jobMatchingAgent;
        this.userProfileRepository = userProfileRepository;
    }

    public JobMatchingResponseDto findRelevantJobs(JobRequest jobRequest, String username){
        UserProfile userProfile = userProfileRepository.findByUser_UsernameIgnoreCase(
                username
        ).orElseThrow(() -> new UserNotFoundException("Username not found"));
        try{
            return jobMatchingAgent.analyzeJobs(jobRequest, userProfile);
        } catch(JsonProcessingException exception){
            throw new FetchJobResponseException("Failed to process job matching JSON");
        }
    }
}



