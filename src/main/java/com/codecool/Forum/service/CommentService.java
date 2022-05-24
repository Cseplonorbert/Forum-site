package com.codecool.Forum.service;

import com.codecool.Forum.exception.CommentNotFoundException;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.reporsitory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new CommentNotFoundException(id);
    }

    public List<Comment> getCommentsByQuestionId(Long id) {
        return commentRepository.findCommentsByQuestionId(id);
    }

    public List<Comment> getCommentsByAnswerId(Long id) {
        return commentRepository.findCommentsByAnswerId(id);
    }

    public Comment add(Question question, Comment comment) {
        return commentRepository.save(
                Comment.builder()
                        .message(comment.getMessage())
                        .question(question)
                        .build());
    }

    public void add(Answer answer, Comment comment) {
        commentRepository.save(
                Comment.builder()
                        .message(comment.getMessage())
                        .answer(answer)
                        .build());
    }

    public Comment update(Long id, Comment updatedComment) {
        Comment comment = getCommentById(id);
        comment.setMessage(updatedComment.getMessage());
        comment.setEditedCount(comment.getEditedCount() + 1);
        comment.setSubmissionTime(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public boolean isBoundedToQuestion(Comment comment) {
        return comment.getQuestion() != null;
    }

    public Comment delete(Long id) {
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
        return comment;
    }
}
