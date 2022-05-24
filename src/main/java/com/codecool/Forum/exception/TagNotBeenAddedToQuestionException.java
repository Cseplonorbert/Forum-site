package com.codecool.Forum.exception;

public class TagNotBeenAddedToQuestionException extends RuntimeException{
    public TagNotBeenAddedToQuestionException(Long questionId, Long tagId) {
        super(String.format("Tag with id %d has not been added question with %d id", tagId, questionId));
    }
}
