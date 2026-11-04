package com.jobmate.jobmate.dto.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ExceptionResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String error;
    private String path;
    Map<String, String> fieldErrors;

    public ExceptionResponseDto(LocalDateTime timestamp, int status, String message, String error, String path, Map<String, String> fieldErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }
}
