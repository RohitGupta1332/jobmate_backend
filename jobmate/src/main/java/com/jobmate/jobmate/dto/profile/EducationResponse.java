package com.jobmate.jobmate.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {
    private Long id;
    private String degree;
    private String specialization;
    private String college;
    private String location;
    private Integer startYear;
    private String endYear;
    private double cgpa;
    private boolean pursuing;
}
