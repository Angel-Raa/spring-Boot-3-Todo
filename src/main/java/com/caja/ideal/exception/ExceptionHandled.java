package com.caja.ideal.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandled  {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(err -> {
                    errors.put("Campo no valido", err.getField());
                });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFound e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", e.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<Object> alreadyExists(AlreadyExists e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> methodValidationInterceptor(ConstraintViolationException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", "invalid field");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> invalidDataException(InvalidDataException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> expiredJwtException(ExpiredJwtException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
