package com.codecool.Forum.exception;

public class TagAlreadyAddedToQuestionException extends RuntimeException{
    public TagAlreadyAddedToQuestionException(Long questionId, Long tagId) {
        super(String.format("Tag with id %d already added question with %d id", tagId, questionId));
    }
}
