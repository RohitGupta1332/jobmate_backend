package com.jobmate.jobmate.model.profile;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "projects")
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 2000) // or columnDefinition = "TEXT"
    private String description;

    @Column(name = "github_url", length = 2048)
    private String githubUrl;

    @Column(name = "live_url", length = 2048)
    private String liveUrl;

    @ElementCollection
    @CollectionTable(name = "project_technologies", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology", length = 255)
    private List<String> technologiesUsed;

    @ElementCollection
    @CollectionTable(name = "project_contributions", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "contribution", length = 2000) // <-- Also defaults to 255 if omitted!
    private List<String> contributions;
}