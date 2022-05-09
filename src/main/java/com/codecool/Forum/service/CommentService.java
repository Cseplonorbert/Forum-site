package com.codecool.Forum.service;

import com.codecool.Forum.exception.CommentNotFoundException;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.reporsitory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new CommentNotFoundException("Comment not found");
    }

    public void addNewComment(Question question, String message) {
        Comment comment = Comment.builder()
                .message(message)
                .question(question)
                .build();
        commentRepository.save(comment);
    }

    public void addNewComment(Answer answer, String message) {
        Comment comment = Comment.builder()
                .message(message)
                .answer(answer).build();
        commentRepository.save(comment);
    }

    public Question update(Long id, String message) {
        Comment comment = findCommentById(id);
        comment.setMessage(message);
        comment.setEditedNumberOfTimes(comment.getEditedNumberOfTimes() + 1);
        comment.setEdited(true);
        comment.setCreatedOn(LocalDateTime.now());
        commentRepository.save(comment);
        if (comment.getQuestion() != null) {
            return comment.getQuestion();
        }
        return comment.getAnswer().getQuestion();
    }
}
