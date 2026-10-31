package com.jobmate.jobmate.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "additional_info")
public class AdditionalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double yearsOfExperience;
    private Double currentCTC;
    private Double expectedCTC;
    private Integer noticePeriod;
    @ElementCollection
    @CollectionTable(
            name = "addtional_certifications",
            joinColumns = @JoinColumn(name = "additional_info_id")
    )
    @Column(name = "certification")
    private List<String> certifications;

}
