package com.fucn.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
