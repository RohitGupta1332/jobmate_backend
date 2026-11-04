package com.jobmate.jobmate.service.profile;

import com.jobmate.jobmate.dto.profile.AdditionalInfoRequest;
import com.jobmate.jobmate.dto.profile.AdditionalInfoResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.profile.AdditionalInfo;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdditionalInfoService extends UserProfileService {
    public AdditionalInfoService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        super(userRepository, userProfileRepository);
    }

    @Transactional
    public AdditionalInfoResponse find(String username) {
        AdditionalInfo info = userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getAdditionalInfo)
                .orElseThrow(() -> new ResourceNotFoundException("Additional information was not found"));
        if (info == null) {
            throw new ResourceNotFoundException("Additional information was not found");
        }
        return toResponse(info);
    }

    @Transactional
    public AdditionalInfoResponse save(String username, AdditionalInfoRequest request) {
        UserProfile profile = findOrCreateProfile(username);
        AdditionalInfo info = profile.getAdditionalInfo();
        if (info == null) {
            info = new AdditionalInfo();
            profile.setAdditionalInfo(info);
        }
        info.setYearsOfExperience(request.getYearsOfExperience());
        info.setCurrentCTC(request.getCurrentCTC());
        info.setExpectedCTC(request.getExpectedCTC());
        info.setNoticePeriod(request.getNoticePeriod());
        info.setCertifications(request.getCertifications() == null
                ? null
                : new ArrayList<>(request.getCertifications()));
        userProfileRepository.save(profile);
        return toResponse(info);
    }

    @Transactional
    public void delete(String username) {
        UserProfile profile = findProfile(username);
        if (profile.getAdditionalInfo() == null) {
            throw new ResourceNotFoundException("Additional information was not found");
        }
        profile.setAdditionalInfo(null);
        userProfileRepository.save(profile);
    }

    private AdditionalInfoResponse toResponse(AdditionalInfo info) {
        return new AdditionalInfoResponse(
                info.getId(),
                info.getYearsOfExperience(),
                info.getCurrentCTC(),
                info.getExpectedCTC(),
                info.getNoticePeriod(),
                info.getCertifications());
    }
}
