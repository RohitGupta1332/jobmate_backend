package com.jobmate.jobmate.model.job;

import com.jobmate.jobmate.dto.job.SalaryDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "jobs")
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String jobId;
    @NotNull
    private String title;
    @NotNull
    private String company;
    @NotNull
    private String location;
    @NotNull
    private String url;
    @Column(columnDefinition = "TEXT")
    @NotNull
    private String description;
    @NotNull
    private Status applicationStatus;
}
