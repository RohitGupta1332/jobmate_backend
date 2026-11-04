package com.jobmate.jobmate.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponse {
    private Long id;
    private String company;
    private String designation;
    private String employmentType;
    private String location;
    private String startDate;
    private String endDate;
    private boolean currentlyWorking;
    private List<String> highlights;
    private String achievements;
    private List<String> technologiesUsed;
}
