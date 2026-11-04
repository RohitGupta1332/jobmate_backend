package com.jobmate.jobmate.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private List<String> technologiesUsed;
    private String githubUrl;
    private String liveUrl;
    private List<String> contributions;
}
