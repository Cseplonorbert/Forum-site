package com.codecool.Forum.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(Long id) {
        super(String.format("Tag with Id %d not found", id));
    }
}
