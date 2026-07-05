package com.vikas.airline.exception;

public class UserDisabledException extends RuntimeException {

    public UserDisabledException(String email) {
        super("User account is disabled: " + email);
    }

}