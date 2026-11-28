package com.jobmate.jobmate.dto.job;

import com.jobmate.jobmate.model.profile.Education;
import com.jobmate.jobmate.model.profile.Experience;
import com.jobmate.jobmate.model.profile.Projects;
import lombok.Data;

import java.util.List;

@Data
public class ResumeDraftDto {

    private String fullName;

    private String professionalSummary;

    private List<String> skills;

    private List<Experience> experiences;

    private List<Projects> projects;

    private List<Education> education;

}