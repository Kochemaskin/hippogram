package com.example.hippogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends ApiException {
    public PostNotFoundException(String message) {
        super(message, "Post not found");
    }
}



