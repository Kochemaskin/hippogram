package com.example.hippogram.exception;

public class InvalidPasswordException extends ApiException {
    public InvalidPasswordException(String wrong_password) {
        super( "Incorrect Account Password", "003");
    }
}
