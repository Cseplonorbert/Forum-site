package com.codecool.Forum.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(String message) {
        super(message);
    }
}
