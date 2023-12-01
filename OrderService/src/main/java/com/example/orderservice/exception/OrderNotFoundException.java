package com.example.orderservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderNotFoundException extends RuntimeException{

    HttpStatus statusCode;

    public OrderNotFoundException() {
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message,HttpStatus statusCode) {
        super(message);
        this.statusCode=statusCode;
    }
}
