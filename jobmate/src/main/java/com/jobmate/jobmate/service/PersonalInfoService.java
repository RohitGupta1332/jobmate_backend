package com.jobmate.jobmate.service;

import com.jobmate.jobmate.dto.PersonalInfoRequest;
import com.jobmate.jobmate.dto.PersonalInfoResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.PersonalInfo;
import com.jobmate.jobmate.model.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoService extends UserProfileService {
    public PersonalInfoService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        super(userRepository, userProfileRepository);
    }

    @Transactional
    public PersonalInfoResponse find(String username) {
        PersonalInfo personalInfo = userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getPersonalInfo)
                .orElseThrow(() -> new ResourceNotFoundException("Personal information was not found"));
        if (personalInfo == null) {
            throw new ResourceNotFoundException("Personal information was not found");
        }
        return toResponse(personalInfo);
    }

    @Transactional
    public PersonalInfoResponse save(String username, PersonalInfoRequest request) {
        UserProfile profile = findOrCreateProfile(username);
        PersonalInfo personalInfo = profile.getPersonalInfo();
        if (personalInfo == null) {
            personalInfo = new PersonalInfo();
            profile.setPersonalInfo(personalInfo);
        }

        personalInfo.setFullName(request.getFullName().trim());
        personalInfo.setEmail(request.getEmail().trim());
        personalInfo.setPhone(request.getPhone());
        personalInfo.setLocation(request.getLocation());
        personalInfo.setLinkedInUrl(request.getLinkedInUrl());
        personalInfo.setGithubUrl(request.getGithubUrl());
        personalInfo.setPortfolio(request.getPortfolio());
        personalInfo.setProfileSummary(request.getProfileSummary());
        userProfileRepository.save(profile);
        return toResponse(personalInfo);
    }

    @Transactional
    public void delete(String username) {
        UserProfile profile = findProfile(username);
        if (profile.getPersonalInfo() == null) {
            throw new ResourceNotFoundException("Personal information was not found");
        }
        profile.setPersonalInfo(null);
        userProfileRepository.save(profile);
    }

    private PersonalInfoResponse toResponse(PersonalInfo personalInfo) {
        return new PersonalInfoResponse(
                personalInfo.getId(),
                personalInfo.getFullName(),
                personalInfo.getEmail(),
                personalInfo.getPhone(),
                personalInfo.getLocation(),
                personalInfo.getLinkedInUrl(),
                personalInfo.getGithubUrl(),
                personalInfo.getPortfolio(),
                personalInfo.getProfileSummary());
    }
}
