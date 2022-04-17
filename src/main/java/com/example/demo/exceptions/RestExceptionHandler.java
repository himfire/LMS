package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException exception){
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(exception.getMessage())
                .httpCode(404)
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidResourceException.class)
    public ResponseEntity<?> handleException(InvalidResourceException exception){
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .httpCode(400)
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
