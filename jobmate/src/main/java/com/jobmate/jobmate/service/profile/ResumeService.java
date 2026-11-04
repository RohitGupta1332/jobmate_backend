package com.jobmate.jobmate.service.profile;

import com.jobmate.jobmate.dto.profile.ResumeResponse;
import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.model.auth.User;
import com.jobmate.jobmate.model.profile.Resume;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import com.jobmate.jobmate.service.resume.CloudinaryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResumeService extends UserProfileService{

    private final CloudinaryService cloudinaryService;

    public ResumeService(
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            CloudinaryService cloudinaryService
            ){
        super(userRepository, userProfileRepository);
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public ResumeResponse uploadResume(String username, MultipartFile file){
        UserProfile userProfile = findOrCreateProfile(username);
        List<Resume> resumes = ensureResume(userProfile);


        Map result = cloudinaryService.uploadPdf(file);

        Resume resume = new Resume();

        resume.setFileName(file.getOriginalFilename());
        resume.setFileUrl((String) result.get("secure_url"));
        resume.setPublicId((String) result.get("public_id"));

        resumes.add(resume);

        userProfileRepository.save(userProfile);
        return toResponse(resume);

    }

    @Transactional
    public void deleteResume(String username, Long resumeId){
        UserProfile userProfile = findProfile(username);
        boolean removed = ensureResume(userProfile).removeIf(resume -> resumeId.equals(resume.getId()));
        if(!removed){
            throw new ResourceNotFoundException(
                    "Resume with id " + resumeId + " was not found for the current user");
        }
    }

    private ResumeResponse toResponse(Resume resume) {
        return new ResumeResponse(
                resume.getId(),
                resume.getFileName(),
                resume.getFileUrl(),
                resume.getPublicId()
        );
    }

    public List<Resume> ensureResume(UserProfile profile){
        if(profile.getResumes() == null){
            profile.setResumes(new ArrayList<>());
        }
        return profile.getResumes();
    }

}
