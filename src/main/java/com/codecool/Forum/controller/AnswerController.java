package com.codecool.Forum.controller;

import com.codecool.Forum.exception.AnswerNotFoundException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    AnswerService answerService;

    @Autowired
    public AnswerController (AnswerService answerService) {
        this.answerService = answerService;
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
}
