package com.codecool.Forum.exception;

public class QuestionNotFoundException extends RuntimeException{

    public QuestionNotFoundException(Long id) {
        super(String.format("Question with Id %d not found", id));
    }
}
