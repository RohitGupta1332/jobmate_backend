package com.jobmate.jobmate.service.profile;

import com.jobmate.jobmate.dto.profile.JobPreferencesRequest;
import com.jobmate.jobmate.dto.profile.JobPreferencesResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.profile.JobPreferences;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JobPreferencesService extends UserProfileService {
    public JobPreferencesService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        super(userRepository, userProfileRepository);
    }

    @Transactional
    public JobPreferencesResponse find(String username) {
        JobPreferences preferences = userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getJobPreferences)
                .orElseThrow(() -> new ResourceNotFoundException("Job preferences were not found"));
        if (preferences == null) {
            throw new ResourceNotFoundException("Job preferences were not found");
        }
        return toResponse(preferences);
    }

    @Transactional
    public JobPreferencesResponse save(String username, JobPreferencesRequest request) {
        UserProfile profile = findOrCreateProfile(username);
        JobPreferences preferences = profile.getJobPreferences();
        if (preferences == null) {
            preferences = new JobPreferences();
            profile.setJobPreferences(preferences);
        }
        preferences.setJobTitle(request.getJobTitle().trim());
        preferences.setPreferredLocations(new ArrayList<>(request.getPreferredLocations()));
        preferences.setEmploymentType(request.getEmploymentType().trim());
        preferences.setNoticePeriod(request.getNoticePeriod());
        preferences.setRelocate(Boolean.TRUE.equals(request.getRelocate()));
        preferences.setPreferredExperienceLevel(request.getPreferredExperienceLevel());
        userProfileRepository.save(profile);
        return toResponse(preferences);
    }

    @Transactional
    public void delete(String username) {
        UserProfile profile = findProfile(username);
        if (profile.getJobPreferences() == null) {
            throw new ResourceNotFoundException("Job preferences were not found");
        }
        profile.setJobPreferences(null);
        userProfileRepository.save(profile);
    }

    private JobPreferencesResponse toResponse(JobPreferences preferences) {
        return new JobPreferencesResponse(
                preferences.getId(),
                preferences.getJobTitle(),
                preferences.getPreferredLocations(),
                preferences.getEmploymentType(),
                preferences.getNoticePeriod(),
                preferences.isRelocate(),
                preferences.getPreferredExperienceLevel());
    }
}
