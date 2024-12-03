package com.nicelink.nicer.exeptions.user;

public class UserAlreadyExistsExeption extends RuntimeException {
  public UserAlreadyExistsExeption(String message) {
    super(message);
  }

  public UserAlreadyExistsExeption(String message, Throwable cause) {
    super(message, cause);
  }
}
