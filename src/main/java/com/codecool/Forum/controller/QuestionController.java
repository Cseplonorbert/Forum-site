package com.codecool.Forum.controller;

import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.service.AnswerService;
import com.codecool.Forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/")
public class QuestionController {

    QuestionService questionService;
    AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/question/{question_id}")
    public Question getQuestionById(@PathVariable Long question_id) {
        try {
            return questionService.getQuestionById(question_id);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question Not Found", exc
            );
        }
    }

    @PostMapping("add-question")
    public ResponseEntity<Question> addQuestion(@RequestParam String title, @RequestParam String description) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.addQuestion(title, description));
    }

    @PostMapping("/question/{question_id}/new-answer")
    public ResponseEntity<Question> addAnswer(@RequestParam String message, @PathVariable Long question_id) {
        try {
            Question question = questionService.getQuestionById(question_id);
            answerService.addNewAnswer(question, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(question);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question Not Found", exc
            );
        }
    }
}
