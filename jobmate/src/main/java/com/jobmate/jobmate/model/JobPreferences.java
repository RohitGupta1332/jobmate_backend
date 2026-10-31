package com.jobmate.jobmate.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "job_preference")
public class JobPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String jobTitle;
    @ElementCollection
    @CollectionTable(
            name = "preferred_location",
            joinColumns = @JoinColumn(name = "location_id")
    )
    @Column(name = "preferred_location", nullable = false)
    private List<String> preferredLocations;
    @Column(nullable = false)
    private String employmentType;
    private Integer noticePeriod;
    @Column(nullable = false)
    private boolean relocate;
    @Column(nullable = false)
    private int preferredExperienceLevel;
}
