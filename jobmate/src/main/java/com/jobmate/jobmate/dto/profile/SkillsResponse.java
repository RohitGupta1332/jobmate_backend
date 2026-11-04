package com.jobmate.jobmate.dto.profile;

import com.jobmate.jobmate.model.profile.Proficiency;
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
