package com.jobmate.jobmate.service;

import com.jobmate.jobmate.dto.AuthRequest;
import com.jobmate.jobmate.dto.LoginResponse;
import com.jobmate.jobmate.dto.RegisterResponse;
import com.jobmate.jobmate.exception.DuplicateUserException;
import com.jobmate.jobmate.model.User;
import com.jobmate.jobmate.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegisterResponse registerUser(AuthRequest request){
            String username = request.getUsername().trim().toLowerCase(Locale.ROOT);
            boolean userExists = userRepository.existsByUsernameIgnoreCase(username);
            if(userExists){
                throw new DuplicateUserException("Username already exists.");
            }
            User user = new User();
            user.setUsername(username);
            String encodedPassword = passwordEncoder.encode((request.getPassword()));
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            User savedUser = userRepository.save(user);
            return new RegisterResponse(username, "registered successfully");
    }

    public LoginResponse loginUser(AuthRequest request) {
        String username = request.getUsername().trim().toLowerCase();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new LoginResponse("Bearer", token, jwtService.getExpirationSeconds());
    }
}
