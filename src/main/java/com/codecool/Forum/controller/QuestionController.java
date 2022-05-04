package com.codecool.Forum.controller;

import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/")
public class QuestionController {

    QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
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
}
