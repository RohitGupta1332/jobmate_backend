package com.jobmate.jobmate.dto.job;

import com.jobmate.jobmate.model.job.Status;
import lombok.Data;

import java.util.List;

@Data
public class JobAnalysisDto {

    private String jobId;
    private String title;
    private String company;
    private String location;
    private String url;
    private Status applicationStatus;
    private String description;
}
