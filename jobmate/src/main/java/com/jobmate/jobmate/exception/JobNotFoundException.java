package com.jobmate.jobmate.exception;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException(String message){
        super(message);
    }
}
