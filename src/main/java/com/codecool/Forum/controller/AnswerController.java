package com.codecool.Forum.controller;

import com.codecool.Forum.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    AnswerService answerService;

    @Autowired
    public AnswerController (AnswerService answerService) {
        this.answerService = answerService;
    }
}
