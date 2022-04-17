package com.example.demo.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {
    private String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
}
