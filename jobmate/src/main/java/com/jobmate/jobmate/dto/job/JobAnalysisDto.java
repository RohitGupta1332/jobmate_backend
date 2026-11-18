package com.jobmate.jobmate.dto.job;

import lombok.Data;

import java.util.List;

@Data
public class JobAnalysisDto {

    private String jobId;
    private String company;
    private String title;
    private Integer matchScore;
    private String summary;
    private List<String> pros;
    private List<String> cons;
}
