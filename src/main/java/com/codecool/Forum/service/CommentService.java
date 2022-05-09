package com.codecool.Forum.service;

import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.reporsitory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addNewComment(Question question, String message) {
        Comment comment = Comment.builder()
                .message(message)
                .question(question)
                .build();
        commentRepository.save(comment);
    }
}
