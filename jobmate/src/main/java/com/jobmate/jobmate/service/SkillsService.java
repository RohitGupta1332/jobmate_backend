package com.jobmate.jobmate.service;

import com.jobmate.jobmate.dto.SkillsRequest;
import com.jobmate.jobmate.dto.SkillsResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.Skills;
import com.jobmate.jobmate.model.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillsService extends UserProfileService {
    public SkillsService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        super(userRepository, userProfileRepository);
    }

    @Transactional
    public SkillsResponse create(String username, SkillsRequest request) {
        UserProfile profile = findOrCreateProfile(username);
        List<Skills> skills = ensureSkills(profile);
        Skills skill = toEntity(request);
        skills.add(skill);
        userProfileRepository.save(profile);
        return toResponse(skill);
    }

    @Transactional
    public List<SkillsResponse> findAll(String username) {
        return userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getSkills)
                .orElseGet(List::of)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public SkillsResponse findById(String username, Long skillId) {
        return toResponse(findSkill(username, skillId));
    }

    @Transactional
    public SkillsResponse update(String username, Long skillId, SkillsRequest request) {
        Skills skill = findSkill(username, skillId);
        updateEntity(skill, request);
        return toResponse(skill);
    }

    @Transactional
    public void delete(String username, Long skillId) {
        UserProfile profile = findProfile(username);
        boolean removed = ensureSkills(profile).removeIf(skill -> skillId.equals(skill.getId()));
        if (!removed) {
            throw skillNotFound(skillId);
        }
        userProfileRepository.save(profile);
    }

    private Skills findSkill(String username, Long skillId) {
        return userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .map(UserProfile::getSkills)
                .orElseGet(List::of)
                .stream()
                .filter(skill -> skillId.equals(skill.getId()))
                .findFirst()
                .orElseThrow(() -> skillNotFound(skillId));
    }

    private List<Skills> ensureSkills(UserProfile profile) {
        if (profile.getSkills() == null) {
            profile.setSkills(new ArrayList<>());
        }
        return profile.getSkills();
    }

    private ResourceNotFoundException skillNotFound(Long skillId) {
        return new ResourceNotFoundException(
                "Skill with id " + skillId + " was not found for the current user");
    }

    private Skills toEntity(SkillsRequest request) {
        Skills skill = new Skills();
        updateEntity(skill, request);
        return skill;
    }

    private void updateEntity(Skills skill, SkillsRequest request) {
        skill.setName(request.getName().trim());
        skill.setProficiency(request.getProficiency());
        skill.setYearExperience(request.getYearExperience());
    }

    private SkillsResponse toResponse(Skills skill) {
        return new SkillsResponse(
                skill.getId(), skill.getName(), skill.getProficiency(), skill.getYearExperience());
    }
}
