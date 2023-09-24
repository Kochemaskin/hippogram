package com.example.hippogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyFollowingException extends ApiException {
    public AlreadyFollowingException(String message) {
        super(message, "Like not found");
    }
}