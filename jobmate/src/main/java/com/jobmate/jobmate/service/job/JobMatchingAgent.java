package com.jobmate.jobmate.service.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobmate.jobmate.dto.job.JobMatchingResponseDto;
import com.jobmate.jobmate.dto.job.JobRequest;
import com.jobmate.jobmate.model.profile.UserProfile;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class JobMatchingAgent {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final JobAgentTools jobAgentTools;

    public JobMatchingAgent(
            ChatClient chatClient,
            ObjectMapper objectMapper,
            JobAgentTools jobAgentTools
    ) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
        this.jobAgentTools = jobAgentTools;
    }

    public JobMatchingResponseDto analyzeJobs(
        JobRequest jobRequest,
        UserProfile userProfile
    ) throws JsonProcessingException {

        String requestJson = objectMapper.writeValueAsString(jobRequest);
        String profileJson = objectMapper.writeValueAsString(userProfile);

        String response = chatClient.prompt()
                .system("""
                    You are an AI job matching agent.
                
                    Your goal is to find at least 10 relevant jobs for the candidate.
                
                    Follow this workflow:
                
                    1. Call ScrapJobs to fetch job listings using the
                       provided job search criteria.
                
                    2. Call removeDuplicates to remove duplicate jobs.
                
                    3. Call filterByExperience to remove jobs that require
                       more experience than the candidate's maximum experience.
                
                    4. Analyze the remaining jobs against the candidate's profile.
                
                    5. If fewer than 10 relevant jobs are found, call ScrapJobs
                       again to fetch additional jobs, if more results are available.
                
                    6. Process newly fetched jobs through deduplication,
                       experience filtering, and matching.
                
                    7. Continue until at least 10 relevant jobs are found
                       or no more jobs can be fetched.
                
                    Rules:
                
                    - Always use the available tools.
                    - Use the output of each tool in subsequent steps.
                    - Do not invent candidate skills or experience.
                    - Do not assume missing information.
                    - Do not mark candidates eligible if mandatory
                      requirements are clearly not satisfied.
                    - Give each job a match score from 0 to 100.
                    - Do not return duplicate jobs.
                    - Return only relevant jobs.
                
                    Return a JSON object with a "results" array.
                
                    Each result must contain:
                    jobId, company, title, matchScore, summary, pros, cons.
                
                    Use strings for jobId, company, title and summary.
                    Use an integer for matchScore.
                    Use arrays of strings for pros and cons.
                
                    Return valid JSON only.
                """)
                .user(user -> user.text("""
                        Find relevant jobs for this candidate.

                        Candidate profile:
                        {profile}

                        Maximum required experience: 2 years.
                        """)
                        .param("profile", profileJson)
                        .param("request", requestJson))
                .tools(jobAgentTools)
                .call()
                .content();

        System.out.println("Gemini response:");
        System.out.println(response);

        String json = extractJson(response);

        return objectMapper.readValue(
                json,
                JobMatchingResponseDto.class
        );
    }

    private String extractJson(String response) {

        String json = response.trim();

        json = json.replaceAll(
                "(?s)^```(?:json)?\\s*|\\s*```$",
                ""
        );

        return json.trim();
    }
}