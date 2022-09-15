package com.kenzie.app.errorHandling;

public class CustomEmptyStringException extends RuntimeException {

    public CustomEmptyStringException (String message) {
        super(message);
    }
}
