package com.codecool.Forum.exception;

public class IllegalPageSizeException extends RuntimeException{
    public IllegalPageSizeException(String message) {
        super(message);
    }
}
