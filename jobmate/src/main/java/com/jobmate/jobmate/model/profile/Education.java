package com.jobmate.jobmate.model.profile;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String degree;
    private String specialization;
    @Column(nullable = false)
    private String college;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Integer startYear;
    private String endYear;
    private double cgpa;
    @Column(nullable = false)
    private boolean pursuing;

}
