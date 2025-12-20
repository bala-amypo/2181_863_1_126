package com.example.demo.exception;

import com.example.demo.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = "Data integrity violation";
        if (e.getMessage().contains("customer_id")) {
            message = "Customer ID already exists";
        } else if (e.getMessage().contains("email")) {
            message = "Email already exists";
        } else if (e.getMessage().contains("phone")) {
            message = "Phone number already exists";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(false, message));
    }
}