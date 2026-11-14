package com.jobmate.jobmate.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobResponseDto {

    // JobSpy's ID is a String, not your database Long ID
    private String id;

    private String site;

    @JsonProperty("job_url")
    private String jobUrl;

    @JsonProperty("job_url_direct")
    private String jobUrlDirect;

    private String title;

    private String company;

    private String location;

    @JsonProperty("date_posted")
    private String datePosted;

    @JsonProperty("job_type")
    private String jobType;

    @JsonProperty("salary_source")
    private String salarySource;

    private String interval;

    @JsonProperty("min_amount")
    private Double minAmount;

    @JsonProperty("max_amount")
    private Double maxAmount;

    private String currency;

    @JsonProperty("is_remote")
    private Boolean remote;

    @JsonProperty("job_level")
    private String jobLevel;

    @JsonProperty("job_function")
    private String jobFunction;

    @JsonProperty("listing_type")
    private String listingType;

    private Object emails;

    private String description;

    @JsonProperty("company_url")
    private String companyUrl;

    @JsonProperty("company_logo")
    private String companyLogo;

    @JsonProperty("company_industry")
    private String companyIndustry;
}