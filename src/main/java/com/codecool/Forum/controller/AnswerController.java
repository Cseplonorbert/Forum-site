package com.codecool.Forum.controller;

import com.codecool.Forum.exception.AnswerNotFoundException;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.service.AnswerService;
import com.codecool.Forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    AnswerService answerService;
    CommentService commentService;

    @Autowired
    public AnswerController (AnswerService answerService, CommentService commentService) {
        this.answerService = answerService;
        this.commentService = commentService;
    }

    @DeleteMapping("/{answer_id}/delete")
    public ResponseEntity<Question> deleteAnswer(@PathVariable Long answer_id) {
        try {
            Question question = answerService.deleteAnswer(answer_id);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (AnswerNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PutMapping("/{answer_id}/vote")
    public ResponseEntity<Question> voteOnAnswer(@PathVariable Long answer_id,
                                               @RequestParam(name = "vote_method") String vote) {
        try {
            Question question = answerService.voteOnAnswer(answer_id, vote);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (AnswerNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PostMapping("/{answer_id}/new-comment")
    public ResponseEntity<Question> addComment(@PathVariable Long answer_id,
                                               @RequestParam String message) {
        try {
            Answer answer = answerService.getAnswerById(answer_id);
            commentService.addNewComment(answer, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(answer.getQuestion());
        } catch (AnswerNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PutMapping("/{answer_id}/edit")
    public ResponseEntity<Question> update(@PathVariable Long answer_id,
                                           @RequestParam String message) {
        try {
            Question question = answerService.update(answer_id, message);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (AnswerNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }
}
