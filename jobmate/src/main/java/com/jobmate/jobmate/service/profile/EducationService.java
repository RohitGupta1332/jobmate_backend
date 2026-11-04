package com.jobmate.jobmate.service.profile;

import com.jobmate.jobmate.dto.profile.EducationRequest;
import com.jobmate.jobmate.dto.profile.EducationResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.profile.Education;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationService extends UserProfileService {

    public EducationService(UserRepository userRepository, UserProfileRepository userProfileRepository){
        super(userRepository, userProfileRepository);
    }
    @Transactional
    public EducationResponse create(String username, EducationRequest request){
        UserProfile profile = findOrCreateProfile(username);
        List<Education> educations = ensureEducation(profile);
        Education education = toEntity(request);
        educations.add(education);
        userProfileRepository.save(profile);
        return toResponse(education);
    }

    @Transactional
    public EducationResponse update(String username, Long educationId, EducationRequest request){
        Education education = findEducation(username, educationId);
        updateEntity(education, request);
        return toResponse(education);
    }

    @Transactional
    public void delete(String username, Long educationId){
        UserProfile userProfile = findProfile(username);
        List<Education> educations = ensureEducation(userProfile);
        boolean removed = educations.removeIf(education -> educationId.equals(education.getId()));
        if (!removed) {
            throw educationNotFound(educationId);
        }
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public List<EducationResponse> findAll(String username){
        return userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getEducation)
                .orElseGet(List::of)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EducationResponse findById(String username, Long educationId){
        Education education = findEducation(username, educationId);
        return toResponse(education);
    }

    private Education findEducation(String username, Long educationId) {
        UserProfile userProfile = findProfile(username);
        List<Education> educations = userProfile.getEducation() == null
                ? List.of()
                : userProfile.getEducation();

        return educations.stream()
                .filter(education -> educationId.equals(education.getId()))
                .findFirst()
                .orElseThrow(() -> educationNotFound(educationId));
    }

    private ResourceNotFoundException educationNotFound(Long educationId) {
        return new ResourceNotFoundException(
                "Education with id " + educationId + " was not found for the current user");
    }

    private EducationResponse toResponse(Education education) {
        return new EducationResponse(
                education.getId(),
                education.getDegree(),
                education.getSpecialization(),
                education.getCollege(),
                education.getLocation(),
                education.getStartYear(),
                education.getEndYear(),
                education.getCgpa(),
                education.isPursuing()
        );
    }

    private List<Education> ensureEducation(UserProfile userProfile){
        if(userProfile.getEducation() == null){
            userProfile.setEducation(new ArrayList<>());
        }
        return userProfile.getEducation();
    }

    private Education toEntity(EducationRequest request){
        Education education = new Education();
        updateEntity(education, request);
        return education;
    }

    private Education updateEntity(Education education, EducationRequest request){
        education.setDegree(request.getDegree());
        education.setCollege(request.getCollege());
        education.setCgpa(request.getCgpa());
        education.setPursuing(request.getPursuing());
        education.setLocation(request.getLocation());
        education.setSpecialization(request.getSpecialization());
        education.setStartYear(request.getStartYear());
        education.setEndYear(request.getEndYear());
        return education;
    }
}
