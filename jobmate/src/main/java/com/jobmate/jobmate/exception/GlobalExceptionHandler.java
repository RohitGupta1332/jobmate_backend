package com.jobmate.jobmate.exception;

import com.jobmate.jobmate.dto.ExceptionResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //handles validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
            ){
        Map<String, String> fieldErrors = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        ExceptionResponseDto response = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Something went wrong",
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    //handles duplicate username
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ExceptionResponseDto> handleDuplicateUsers(
            DuplicateUserException exception,
            HttpServletRequest request
    ){
        ExceptionResponseDto response = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Username already exists",
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getRequestURI(),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleUserNotFound(
            UserNotFoundException exception,
            HttpServletRequest request
            ){
        ExceptionResponseDto response = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "username not found",
                HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                request.getRequestURI(),
                new HashMap<>()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    //handle other runtime server errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleExceptions(Exception exception, HttpServletRequest request){
        ExceptionResponseDto response = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                request.getRequestURI(),
                new HashMap<>()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
