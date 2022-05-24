package com.codecool.Forum.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super(String.format("Answer with Id %d not found", id));
    }
}
