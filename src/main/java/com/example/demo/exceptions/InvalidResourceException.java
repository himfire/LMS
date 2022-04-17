package com.example.demo.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class InvalidResourceException extends RuntimeException {
    private String errorMessage;
    private List<String> errors;

    public InvalidResourceException(String errorMessage, List<String> errors) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errors = errors;
    }
    public InvalidResourceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
