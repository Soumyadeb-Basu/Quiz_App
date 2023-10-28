package com.soumya.quizapp.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    private final HttpStatus errorCode;

    private final String errorMessage;

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ResourceNotFoundException(HttpStatus errorCode, String errorMessage) {
        super();
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }




}
