package com.codecool.Forum.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long id) {
        super(String.format("Comment with Id %d not found", id));
    }
}
