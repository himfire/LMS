package com.example.demo.exceptions;

import lombok.Data;

import java.util.List;
@Data
public class EntityNotFoundException extends RuntimeException {

    private String errorMessage;

    public EntityNotFoundException(String errorMessage, List<String> errors) {
        super(errorMessage);
        this.errorMessage = errorMessage;

    }
    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
