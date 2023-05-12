package com.caja.ideal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException{
    private String message;

    public InvalidDataException(String message) {
        this.message = message;
    }
}
