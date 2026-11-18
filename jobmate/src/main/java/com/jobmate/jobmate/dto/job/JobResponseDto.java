
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

    @JsonProperty("job_type")
    private String jobType;

    @JsonProperty("job_level")
    private String jobLevel;

    @JsonProperty("job_function")
    private String jobFunction;

    @JsonProperty("listing_type")
    private String listingType;

    // Salary information
    @JsonProperty("salary_source")
    private String salarySource;

    private String interval;

    @JsonProperty("min_amount")
    private Double minAmount;

    @JsonProperty("max_amount")
    private Double maxAmount;

    // Work arrangement
    @JsonProperty("is_remote")
    private Boolean remote;

    @JsonProperty("work_from_home_type")
    private String workFromHomeType;

    // Skills and experience
    private String skills;

    @JsonProperty("experience_range")
    private String experienceRange;

    // Job description
    private String description;

    // Company information
    @JsonProperty("company_url")
    private String companyUrl;

    @JsonProperty("company_url_direct")
    private String companyUrlDirect;

    @JsonProperty("company_logo")
    private String companyLogo;

    @JsonProperty("company_industry")
    private String companyIndustry;

    @JsonProperty("company_addresses")
    private String companyAddresses;

    @JsonProperty("company_num_employees")
    private String companyNumEmployees;

    @JsonProperty("company_rating")
    private Double companyRating;

    @JsonProperty("company_reviews_count")
    private Integer companyReviewsCount;

    // Vacancy information
    @JsonProperty("vacancy_count")
    private Integer vacancyCount;
}
