package com.jobmate.jobmate.service.profile;

import com.jobmate.jobmate.dto.profile.ExperienceRequest;
import com.jobmate.jobmate.dto.profile.ExperienceResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.profile.Experience;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExperienceService extends UserProfileService {

    public ExperienceService(
            UserProfileRepository userProfileRepository,
            UserRepository userRepository) {
        super(userRepository, userProfileRepository);

    }

    @Transactional
    public ExperienceResponse create(String username, ExperienceRequest request) {
        UserProfile profile = userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .orElseGet(() -> findOrCreateProfile(username));

        List<Experience> experiences = ensureExperiences(profile);
        Experience experience = toEntity(request);
        experiences.add(experience);

        userProfileRepository.save(profile);
        return toResponse(experience);
    }

    @Transactional
    public List<ExperienceResponse> findAll(String username) {
        return userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getExperiences)
                .orElseGet(List::of)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ExperienceResponse findById(String username, Long experienceId) {
        return toResponse(findExperience(username, experienceId));
    }

    @Transactional
    public ExperienceResponse update(
            String username,
            Long experienceId,
            ExperienceRequest request) {
        Experience experience = findExperience(username, experienceId);
        updateEntity(experience, request);
        return toResponse(experience);
    }

    @Transactional
    public void delete(String username, Long experienceId) {
        UserProfile profile = findProfile(username);
        List<Experience> experiences = ensureExperiences(profile);
        boolean removed = experiences.removeIf(experience -> experienceId.equals(experience.getId()));
        if (!removed) {
            throw experienceNotFound(experienceId);
        }

        userProfileRepository.save(profile);
    }


    private Experience findExperience(String username, Long experienceId) {
        return getExperiences(findProfile(username)).stream()
                .filter(experience -> experienceId.equals(experience.getId()))
                .findFirst()
                .orElseThrow(() -> experienceNotFound(experienceId));
    }

    private List<Experience> getExperiences(UserProfile profile) {
        return profile.getExperiences() == null
                ? List.of()
                : profile.getExperiences();
    }

    private List<Experience> ensureExperiences(UserProfile profile) {
        if (profile.getExperiences() == null) {
            profile.setExperiences(new ArrayList<>());
        }
        return profile.getExperiences();
    }

    private ResourceNotFoundException experienceNotFound(Long experienceId) {
        return new ResourceNotFoundException(
                "Experience with id " + experienceId + " was not found for the current user");
    }

    private Experience toEntity(ExperienceRequest request) {
        Experience experience = new Experience();
        updateEntity(experience, request);
        return experience;
    }

    private void updateEntity(Experience experience, ExperienceRequest request) {
        experience.setCompany(request.getCompany().trim());
        experience.setDesignation(request.getDesignation().trim());
        experience.setEmploymentType(request.getEmploymentType().trim());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentlyWorking(Boolean.TRUE.equals(request.getCurrentlyWorking()));
        experience.setHighlights(request.getHighlights() == null
                ? null
                : new ArrayList<>(request.getHighlights()));
        experience.setAchievements(request.getAchievements());
        experience.setTechnologiesUsed(request.getTechnologiesUsed() == null
                ? null
                : new ArrayList<>(request.getTechnologiesUsed()));
    }

    private ExperienceResponse toResponse(Experience experience) {
        return new ExperienceResponse(
                experience.getId(),
                experience.getCompany(),
                experience.getDesignation(),
                experience.getEmploymentType(),
                experience.getLocation(),
                experience.getStartDate(),
                experience.getEndDate(),
                experience.isCurrentlyWorking(),
                experience.getHighlights(),
                experience.getAchievements(),
                experience.getTechnologiesUsed());
    }
}
