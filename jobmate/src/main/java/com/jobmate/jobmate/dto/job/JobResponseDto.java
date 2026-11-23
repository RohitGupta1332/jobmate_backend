
package com.jobmate.jobmate.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JobResponseDto {

    // Job identification
    private String id;
    private String site;

    @JsonProperty("job_url")
    private String jobUrl;

    @JsonProperty("job_url_direct")
    private String jobUrlDirect;

    // Basic job information
    private String title;
    private String company;
    private String location;

    @JsonProperty("date_posted")
    private String datePosted;

    @JsonProperty("min_amount")
    private Double minAmount;

    @JsonProperty("max_amount")
    private Double maxAmount;

    // Skills and experience
    private String skills;

    @JsonProperty("experience_range")
    private String experienceRange;

    // Job description
    private String description;

}
