package com.jobmate.jobmate.service.resume;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobmate.jobmate.dto.job.ResumeDraftDto;
import com.jobmate.jobmate.dto.job.UserResumeDto;
import com.jobmate.jobmate.model.job.Jobs;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ResumeAiService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public ResumeAiService(
            ChatClient.Builder chatClientBuilder,
            ObjectMapper objectMapper
    ) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public ResumeDraftDto generateResume(
            UserResumeDto userProfile,
            Jobs job
    ) throws JsonProcessingException {

        String profileJson =
                objectMapper.writeValueAsString(userProfile);

        String jobJson =
                objectMapper.writeValueAsString(job);

        return chatClient.prompt()

                .system("""
                            You are an expert ATS Resume Optimization Agent.
                                                
                            Your task is to generate a professional, ATS-friendly resume by analyzing the candidate's profile against a specific job description.
                                                
                            ### 1. Job Analysis
                            - Identify required technical skills, preferred skills, responsibilities and qualifications.
                            - Identify important keywords used in the job description.
                            - Determine which candidate experiences and projects are most relevant.
                                                
                            ### 2. Candidate Profile Analysis
                            - Analyze all candidate information.
                            - Identify relevant experience, skills, projects and achievements.
                            - Prioritize information based on relevance to the target job.
                            - Identify gaps between the candidate's profile and job requirements.
                                                
                            ### 3. Resume Optimization
                            - Write a concise, job-specific professional summary.
                            - Reorder skills based on job relevance.
                            - Reorder experiences and projects based on relevance.
                            - Rewrite existing experience highlights using strong action verbs.
                            - Include relevant job-description keywords naturally.
                            - Make bullet points concise, measurable where supported, and achievement-oriented.
                            - Remove duplicate and irrelevant information where appropriate.
                                                
                            ### 4. Strict Accuracy Rules
                            - NEVER invent experience, skills, achievements, certifications, metrics or projects.
                            - NEVER change company names, designations, employment dates or education details.
                            - NEVER claim that the candidate has a skill simply because it appears in the job description.
                            - Do not exaggerate responsibilities or ownership.
                            - Preserve all factual information.
                            - Do not calculate or assume total years of experience unless the dates are sufficient and unambiguous.
                            - Do not fill missing information with assumptions.
                                                
                            ### 5. ATS Formatting
                            - Use standard resume section names.
                            - Avoid tables, graphics, icons and decorative formatting.
                            - Use standard professional terminology.
                            - Keep bullet points concise.
                            - Avoid keyword stuffing.
                            - Prioritize readability and relevance.
                                                
                            ### 6. Output
                            Return structured resume JSON matching the provided ResumeDraftDto schema.
                                                
                            Exclude empty fields and duplicate information.
                            Return only the structured resume data.
                        """)

                .user(u -> u.text("""
                    Candidate Profile:
                    {profile}

                    Job Description:
                    {job}

                    Generate an ATS-friendly resume for this job.
                    """)
                        .param("profile", profileJson)
                        .param("job", jobJson))

                .call()

                .entity(ResumeDraftDto.class);
    }
}