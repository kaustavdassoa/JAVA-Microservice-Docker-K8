package com.example.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException onfException)
    {
        return new ResponseEntity<>(ErrorResponse.builder().errorMessage(onfException.getMessage())
                .errorCode("ORDER_NOT_FOUND").build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException pnfException)
    {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(pnfException.getMessage())
                .errorCode("PRODUCT_NOT_FOUND").build(),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException InsufficientStockException)
    {
        //TODO : ReplenishStock function

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(InsufficientStockException.getMessage())
                .errorCode("IN_SUFFICIENT_INVENTORY").build(),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RemoteException.class)
    public ResponseEntity<ErrorResponse> handleRemoteException(RemoteException remoteException)
    {
       return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(remoteException.getMessage())
                .errorCode("REMOTE EXCEPTION -"+remoteException.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
    }
}