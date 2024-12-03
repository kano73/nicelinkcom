package com.nicelink.nicer.exeptions.link;

public class LinkAlreadyExistsException extends RuntimeException {
    public LinkAlreadyExistsException(String message) {
        super(message);
    }

    public LinkAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
