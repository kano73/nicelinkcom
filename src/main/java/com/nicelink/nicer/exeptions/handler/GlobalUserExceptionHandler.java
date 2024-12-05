package com.nicelink.nicer.exeptions.handler;

import com.nicelink.nicer.exeptions.link.InvalidLinkException;
import com.nicelink.nicer.exeptions.link.LinkAlreadyExistsException;
import com.nicelink.nicer.exeptions.link.LinkNotFoundException;
import com.nicelink.nicer.exeptions.link.UserDoesNotOwnThisLinkException;
import com.nicelink.nicer.exeptions.user.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalUserExceptionHandler {

    public String baseUrl = "http://192.168.0.94:8080";

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> hadlePasswordException (InvalidPasswordException exception){
        return new ResponseEntity<String>("Invalid password", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> hadleUserException (InvalidUserException exception){
        return new ResponseEntity<String>("Invalid user info",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistsExeption.class)
    public ResponseEntity<String> hadleExistsExeption (UserAlreadyExistsExeption exception){
        return new ResponseEntity<String>("User already exists",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> hadleUserNotFoundException (UserNotFoundException exception){
        return new ResponseEntity<String>("User not found",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> hadleValidationException (ValidationException exception){
        return new ResponseEntity<String>("validation failed",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidLinkException.class)
    public ResponseEntity<String> hadleInvalidLinkException (InvalidLinkException exception){
        return new ResponseEntity<String>("invalid link",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ResponseEntity<String> hadleLinkAlreadyExistsException (LinkAlreadyExistsException exception){
        return new ResponseEntity<String>("link already exists",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<String> hadleLinkNotFoundException (LinkNotFoundException exception){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Refresh", "0.1; URL=" + baseUrl+"/notfound");

        return ResponseEntity.ok().headers(headers).body("link not found");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> hadleRuntimeException (RuntimeException exception){
        return new ResponseEntity<String>(String.valueOf(exception),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> hadleAuthenticationException (AuthenticationException exception){
        return new ResponseEntity<String>("Error accused while determining authenticated user",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<String> hadleClassCastException (ClassCastException exception){
        return new ResponseEntity<String>("User does not authenticated",HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotOwnThisLinkException.class)
    public ResponseEntity<String> hadleUserDoesNotOwnThisLinkException (UserDoesNotOwnThisLinkException exception){
        return new ResponseEntity<String>("Sorry, but this is not your link: <p>We can't show you this private info</p>",HttpStatus.CONFLICT);
    }

}
