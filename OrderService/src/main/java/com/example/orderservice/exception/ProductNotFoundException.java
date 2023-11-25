package com.example.orderservice.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException{

    HttpStatus statusCode;
    public ProductNotFoundException(String s) {
        super(s);
        this.statusCode=HttpStatus.NOT_FOUND;
    }

    public ProductNotFoundException(String s,HttpStatus statusCode) {
        super(s);
        this.statusCode=statusCode;
    }
}
