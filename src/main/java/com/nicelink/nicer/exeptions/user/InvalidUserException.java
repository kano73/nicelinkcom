package com.nicelink.nicer.exeptions.user;

public class InvalidUserException extends Exception {

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
