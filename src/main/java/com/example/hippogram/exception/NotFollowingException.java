package com.example.hippogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotFollowingException extends ApiException {
    public NotFollowingException(String message) {
        super(message, "Like not found");
    }
}