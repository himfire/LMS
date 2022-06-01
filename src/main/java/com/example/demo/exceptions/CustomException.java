package com.example.demo.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

//@Builder
@Data
@Builder
@NoArgsConstructor
public class CustomException extends RuntimeException {
    private String code;
    private HttpStatus status;
    private List<String> errors;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String code, HttpStatus status, List<String> errors) {
        super();
        this.code = code;
        this.status = status;
        this.errors = errors;
    }
    public CustomException(String code, HttpStatus status, List<String> errors, String message) {
        super(message);
        this.code = code;
        this.status = status;
        this.errors = errors;
    }
}
