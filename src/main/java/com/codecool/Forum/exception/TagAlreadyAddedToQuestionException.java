package com.codecool.Forum.exception;

public class TagAlreadyAddedToQuestionException extends RuntimeException{
    public TagAlreadyAddedToQuestionException(String message) {
        super(message);
    }
}
