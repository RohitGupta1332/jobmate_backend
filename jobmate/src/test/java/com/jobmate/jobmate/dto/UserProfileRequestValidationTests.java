package com.jobmate.jobmate.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserProfileRequestValidationTests {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void cascadesValidationToNestedProfileDetails() {
        UserProfileRequest request = new UserProfileRequest();
        request.setPersonalInfo(new PersonalInfoRequest(null, "not-an-email", null, null, null, null, null, null));
        request.setExperiences(List.of(new ExperienceRequest()));

        Set<ConstraintViolation<UserProfileRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(violation ->
                violation.getPropertyPath().toString().equals("personalInfo.fullName")));
        assertTrue(violations.stream().anyMatch(violation ->
                violation.getPropertyPath().toString().equals("personalInfo.email")));
        assertTrue(violations.stream().anyMatch(violation ->
                violation.getPropertyPath().toString().equals("experiences[0].company")));
    }

    @Test
    void acceptsValidNestedProfileDetails() {
        UserProfileRequest request = new UserProfileRequest();
        request.setPersonalInfo(new PersonalInfoRequest(
                "Alex Example", "alex@example.com", null, null, null, null, null, null));
        request.setExperiences(List.of(new ExperienceRequest(
                "Example Inc.", "Developer", "Full-time", null, "2024-01-01", null,
                false, null, null, null)));

        assertFalse(validator.validate(request).iterator().hasNext());
    }
}
