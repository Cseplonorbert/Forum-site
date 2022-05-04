package com.codecool.Forum.service;

import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.model.OrderBy;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.reporsitory.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions(String orderBy, String orderDirection) {
        Sort sort;
        if (orderBy == null || orderDirection == null) {
            sort = Sort.by(Sort.Direction.DESC, "createdOn");
        } else {
            sort = Sort.by(Sort.Direction.valueOf(orderDirection),
                    OrderBy.valueOf(orderBy).getFieldName());
        }
        return questionRepository.findAll(sort);
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findQuestionById(id);
        if (question.isPresent()) {
            return question.get();
        }
        throw new QuestionNotFoundException("Question not found");
    }

    public Question addQuestion(String title, String description) {
        Question question = Question.builder().title(title).description(description).build();
        return questionRepository.save(question);
    }

    public void deleteQuestionById(Long id) {
        Question question = getQuestionById(id);
        questionRepository.delete(question);
    }
}
