package com.jobmate.jobmate.service.profile;

import com.jobmate.jobmate.exception.ResourceNotFoundException;
import com.jobmate.jobmate.exception.UserNotFoundException;
import com.jobmate.jobmate.model.auth.User;
import com.jobmate.jobmate.model.profile.UserProfile;
import com.jobmate.jobmate.repository.UserProfileRepository;
import com.jobmate.jobmate.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    protected final UserProfileRepository userProfileRepository;
    protected final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository, UserProfileRepository userProfileRepository){
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    protected UserProfile createProfile(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        return profile;
    }
    protected UserProfile findOrCreateProfile(String username) {
        return userProfileRepository
                .findByUser_UsernameIgnoreCase(username)
                .orElseGet(() -> createProfile(username));
    }

    protected UserProfile findProfile(String username) {
        return userProfileRepository.findByUser_UsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User profile was not found"));
    }
}
