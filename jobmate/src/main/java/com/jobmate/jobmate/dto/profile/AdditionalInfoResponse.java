package com.jobmate.jobmate.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoResponse {
    private Long id;
    private Double yearsOfExperience;
    private Double currentCTC;
    private Double expectedCTC;
    private Integer noticePeriod;
    private List<String> certifications;
}
