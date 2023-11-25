package com.example.productservice.exception;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends RuntimeException {
   HttpStatus statusCode;

    public InsufficientStockException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public InsufficientStockException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
