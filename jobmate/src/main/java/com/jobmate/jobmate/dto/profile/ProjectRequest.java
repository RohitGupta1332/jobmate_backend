package com.jobmate.jobmate.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @Size(max = 100)
    private List<@NotBlank @Size(max = 255) String> technologiesUsed;

    @Size(max = 2048)
    @Pattern(regexp = "^https?://\\S+$", message = "GitHub URL must start with http:// or https://")
    private String githubUrl;

    @Size(max = 2048)
    @Pattern(regexp = "^https?://\\S+$", message = "Live URL must start with http:// or https://")
    private String liveUrl;

    @Size(max = 100)
    private List<@NotBlank @Size(max = 2000) String> contributions;
}
