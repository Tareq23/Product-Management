package com.qtec.pm.application.exception;

public class ProductNotFoundException extends RuntimeException{
    String message;
    public ProductNotFoundException(String message){
        super(message);
    }
}
