package com.jobmate.jobmate.exception;

public class RequiredFieldMissingException extends RuntimeException{
    public RequiredFieldMissingException(String message){
        super(message);
    }
}
