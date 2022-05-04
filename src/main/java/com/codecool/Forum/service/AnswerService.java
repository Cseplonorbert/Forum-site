package com.codecool.Forum.service;

import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.reporsitory.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void addNewAnswer(Question question, String message) {
        Answer answer = Answer.builder()
                .message(message)
                .question(question)
                .build();
        answerRepository.save(answer);
    }
}
