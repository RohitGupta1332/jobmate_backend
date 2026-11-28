package com.jobmate.jobmate.dto.job;

import com.jobmate.jobmate.model.auth.User;
import com.jobmate.jobmate.model.profile.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResumeDto {

    private User user;

    private PersonalInfo personalInfo;

    private AdditionalInfo additionalInfo;

    private List<Experience> experiences;

    private List<Skills> skills;

    private List<Education> education;

    private List<Projects> projects;

}
