package com.example.orderservice.exception;

import org.springframework.http.HttpStatus;

public class RemoteException extends RuntimeException{
    String errorCode;

    public RemoteException(String errorCode) {
        this.errorCode = errorCode;
    }

    public RemoteException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
