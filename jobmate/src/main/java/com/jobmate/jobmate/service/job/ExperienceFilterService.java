
package com.jobmate.jobmate.service.job;

import com.jobmate.jobmate.dto.job.JobResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExperienceFilterService {

    private static final Pattern RANGE_PATTERN = Pattern.compile(
            "(?i)(\\d+)\\s*(?:-|–|—|to)\\s*(\\d+)\\s*(?:years?|yrs?)\\b"
    );

    private static final Pattern PLUS_PATTERN = Pattern.compile(
            "(?i)(\\d+)\\s*\\+\\s*(?:years?|yrs?)\\b"
    );

    private static final Pattern MINIMUM_PATTERN = Pattern.compile(
            "(?i)(?:minimum\\s+(?:of\\s+)?|at\\s+least\\s+)(\\d+)\\s*(?:years?|yrs?)\\b"
    );

    private static final Pattern SINGLE_PATTERN = Pattern.compile(
            "(?i)(\\d+)\\s*(?:years?|yrs?)\\s+(?:of\\s+)?(?:relevant\\s+)?experience"
    );

    public boolean isExperienceRelevant(
            JobResponseDto job,
            int maxExperience
    ) {

        // First check structured experience field
        Integer requiredExperience =
                extractMinimumExperience(job.getExperienceRange());

        // If unavailable, extract from description
        if (requiredExperience == null) {
            requiredExperience =
                    extractMinimumExperience(job.getDescription());
        }

        // Strict filtering: unknown experience is not considered eligible
        if (requiredExperience == null) {
            return false;
        }

        return requiredExperience <= maxExperience;
    }

    private Integer extractMinimumExperience(String text) {

        if (text == null || text.isBlank()) {
            return null;
        }

        Matcher matcher = RANGE_PATTERN.matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        matcher = PLUS_PATTERN.matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        matcher = MINIMUM_PATTERN.matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        matcher = SINGLE_PATTERN.matcher(text);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return null;
    }
}
