package com.example.hippogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LikeNotFoundException extends ApiException {
    public LikeNotFoundException(String message) {
        super(message, "Like not found");
    }
}
