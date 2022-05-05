package com.codecool.Forum.controller;

import com.codecool.Forum.exception.AnswerNotFoundException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
