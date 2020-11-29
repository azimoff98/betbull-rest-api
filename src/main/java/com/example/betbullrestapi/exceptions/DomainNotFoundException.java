package com.example.betbullrestapi.exceptions;

public class DomainNotFoundException extends RuntimeException{

    public DomainNotFoundException(String message) {
        super(message);
    }
}
