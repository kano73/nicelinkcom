package com.nicelink.nicer.exeptions.link;

public class LinkNotFoundException extends RuntimeException {
    public LinkNotFoundException(String message) {
        super(message);
    }

    public LinkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
