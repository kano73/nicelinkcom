package com.nicelink.nicer.exeptions.link;

public class UserDoesNotOwnThisLinkException extends RuntimeException {
    public UserDoesNotOwnThisLinkException(String message) {
        super(message);
    }

    public UserDoesNotOwnThisLinkException(String message, Throwable cause){
        super(message, cause);
    }
}
