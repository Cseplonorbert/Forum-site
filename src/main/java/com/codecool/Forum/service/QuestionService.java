package com.codecool.Forum.service;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.reporsitory.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll(Sort.by(Sort.Direction.ASC, "createdOn"));
    }
}
