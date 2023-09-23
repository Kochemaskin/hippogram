package com.example.hippogram.exception;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String user_not_found) {
        super("Account not Exists", "002");
    }
}
