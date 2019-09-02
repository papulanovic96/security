package com.megatravel.exception;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException(String message){
        super(message);
    }
}
