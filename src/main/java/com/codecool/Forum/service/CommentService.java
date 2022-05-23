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

    public List<Comment> getCommentsByQuestionId(Long id) {
        return commentRepository.findCommentsByQuestionId(id);
    }

    public Comment add(Question question, Comment comment) {
        return commentRepository.save(
                Comment.builder()
                        .message(comment.getMessage())
                        .question(question)
                        .build());
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
        comment.setEditedCount(comment.getEditedCount() + 1);
        comment.setSubmissionTime(LocalDateTime.now());
        commentRepository.save(comment);
        return getQuestion(comment);
    }

    public Question delete(Long id) {
        Comment comment = findCommentById(id);
        commentRepository.delete(comment);
        return getQuestion(comment);
    }

    private Question getQuestion(Comment comment) {
        if (comment.getQuestion() != null) {
            return comment.getQuestion();
        }
        return comment.getAnswer().getQuestion();
    }
}
