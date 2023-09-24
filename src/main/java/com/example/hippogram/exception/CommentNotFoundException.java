package com.example.hippogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends ApiException {
    public CommentNotFoundException(String message) {
        super(message, "Comment not found");
    }
}

