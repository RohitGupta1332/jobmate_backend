
package com.jobmate.jobmate.service.job;

import com.jobmate.jobmate.dto.job.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class JobMatchingAgent {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public JobMatchingAgent(
            ChatClient chatClient,
            ObjectMapper objectMapper
    ) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }


    public JobMatchingResponseDto analyzeJobs(
            List<JobResponseDto> jobs,
            UserProfileForAgentDto profile
    ) {

        if (jobs == null || jobs.isEmpty()) {
            return new JobMatchingResponseDto();
        }

        try {
            String jobsJson = objectMapper.writeValueAsString(jobs);
            String profileJson = objectMapper.writeValueAsString(profile);

            ChatResponse chatResponse = callLlmWithRetry(
                    jobsJson,
                    profileJson
            );

            // Token consumption
            Usage usage = chatResponse.getMetadata().getUsage();

            System.out.println("---------- TOKEN USAGE ----------");
            System.out.println("Prompt tokens: " + usage.getPromptTokens());
            System.out.println("Completion tokens: " + usage.getCompletionTokens());
            System.out.println("Total tokens: " + usage.getTotalTokens());
            System.out.println("---------------------------------");

            String response = chatResponse.getResult()
                    .getOutput()
                    .getText();

            if (response == null || response.isBlank()) {
                return new JobMatchingResponseDto();
            }

            return objectMapper.readValue(
                    extractJson(response),
                    JobMatchingResponseDto.class
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to analyze jobs using LLM", e
            );
        }
    }


    private ChatResponse callLlmWithRetry(
            String jobsJson,
            String profileJson
    ) {

        int maxRetries = 3;
        long waitTime = 12000; // 12 seconds

        for (int attempt = 1; attempt <= maxRetries + 1; attempt++) {

            try {

                System.out.println(
                        "LLM request attempt: " + attempt
                );

                return chatClient.prompt()

                        .system("""
                            You are an expert technical recruiter.

                            Your task is to identify jobs relevant to the
                            candidate based on their profile.

                            Evaluate each job based on:
                            1. Technical skills match.
                            2. Relevant work experience.
                            3. Required qualifications.
                            4. Project experience.
                            5. Job responsibilities.

                            Rules:
                            - Only select jobs from the provided job list.
                            - Never invent job IDs.
                            - Do not modify job details.
                            - Do not select jobs that clearly exceed
                              the candidate's qualifications.
                            - Return only the IDs of relevant jobs.
                            - If no jobs are relevant, return an empty list.

                            Return valid JSON in this format:
                            {
                              "results": [
                                {
                                  "jobId": "job-101",
                                  "title": "Java Developer",
                                  "company": "ABC",
                                  "location": "Bangalore",
                                  "url": "https://example.com",
                                  "description": "Backend development"
                                }
                              ]
                            }
                            """)

                        .user(user -> user.text("""
                            Candidate Profile:
                            {profile}

                            Available Jobs:
                            {jobs}

                            Identify the relevant jobs for this candidate.
                            """)
                                .param("profile", profileJson)
                                .param("jobs", jobsJson))

                        .call()
                        .chatResponse();

            } catch (com.openai.errors.RateLimitException e) {

                if (attempt > maxRetries) {
                    System.out.println(
                            "Maximum retries reached. Stopping LLM request."
                    );

                    throw e;
                }

                System.out.println(
                        "Rate limit reached. Waiting " +
                                (waitTime / 1000) + " seconds before retry."
                );

                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "LLM retry interrupted",
                            interruptedException
                    );
                }

                // Exponential backoff
                waitTime = Math.min(waitTime * 2, 60000);
            }
        }

        throw new RuntimeException("LLM request failed");
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
