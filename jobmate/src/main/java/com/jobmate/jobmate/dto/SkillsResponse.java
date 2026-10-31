package com.jobmate.jobmate.dto;

import com.jobmate.jobmate.model.Proficiency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillsResponse {
    private Long id;
    private String name;
    private Proficiency proficiency;
    private Double yearExperience;
}
