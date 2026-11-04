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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ElementCollection
    @CollectionTable(
            name = "technologies_used",
            joinColumns = @JoinColumn(name = "technology_id")
    )
    @Column(name = "technology", nullable = false)
    private List<String> technologiesUsed;
    private String githubUrl;
    private String liveUrl;
    @Column(name = "contribution")
    @ElementCollection
    @CollectionTable(
            name = "project_contribution",
            joinColumns = @JoinColumn(name = "project_id")
    )
    private List<String> contributions;
}
