package com.jobmate.jobmate.model.profile;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "work_experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private String designation;
    @Column(nullable = false)
    private String employmentType;
    private String location;
    @Column(nullable = false)
    private String startDate;
    private String endDate;
    private boolean currentlyWorking;
    @ElementCollection
    @CollectionTable(
            name = "experience_highlights",
            joinColumns = @JoinColumn(name = "experience_id")
    )
    @Column(name = "highlight", nullable = false, length = 2000)
    private List<String> highlights;
    private String achievements;
    @ElementCollection
    @CollectionTable(
            name = "experience_technologies",
            joinColumns = @JoinColumn(name = "technology_id")
    )
    @Column(name = "technology")
    private List<String> technologiesUsed;

}
