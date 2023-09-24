package com.example.hippogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LikeAlreadyExistsException extends ApiException {
    public LikeAlreadyExistsException(String message) {
        super(message, "Like not found");
    }
}