package com.megatravel.exception;

public class BadParametersException extends RuntimeException {
    public BadParametersException(String message){
        super("Parameters in path and in body are different! " + message);
    }
}
