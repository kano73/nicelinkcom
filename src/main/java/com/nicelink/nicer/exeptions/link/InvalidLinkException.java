package com.nicelink.nicer.exeptions.link;

public class InvalidLinkException extends Exception {
    public InvalidLinkException(String message) {
        super(message);
    }

    public InvalidLinkException(String message, Throwable cause) {
        super(message, cause);
    }
}
