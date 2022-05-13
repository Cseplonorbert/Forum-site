package com.codecool.Forum.service;

import com.codecool.Forum.exception.IllegalPageSizeException;
import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.model.OrderBy;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Vote;
import com.codecool.Forum.reporsitory.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {

    QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> findAll(String order, String orderBy, Integer page, Integer pageSize) {
        Pageable pageable = getPageable(order, orderBy, page, pageSize);
        return questionRepository.findAll(pageable);
    }

    public Page<Question> search(String phrase, String order, String orderBy, Integer page, Integer pageSize) {
        Pageable pageable = getPageable(order, orderBy, page, pageSize);
        return questionRepository.
                findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(pageable, phrase, phrase);
    }

    private Pageable getPageable(String order, String orderBy, Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.valueOf(order),
                OrderBy.valueOf(orderBy).getFieldName());

        if (pageSize > 50 || pageSize < 1) {
            throw new IllegalPageSizeException("Invalid pageSize, it must be between 1 and 50");
        }

        return PageRequest.of(page, pageSize, sort);
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findQuestionById(id);
        if (question.isPresent()) {
            return question.get();
        }
        throw new QuestionNotFoundException("Question not found");
    }

    public Question add(Question question) {
        return questionRepository.save(Question.builder()
                .title(question.getTitle())
                .description(question.getDescription())
                .build());
    }

    public void delete(Long id) {
        Question question = getQuestionById(id);
        questionRepository.delete(question);
    }

    public Question update(Long id, Question editedQuestion) {
        Question question = getQuestionById(id);
        question.setTitle(editedQuestion.getTitle());
        question.setDescription(editedQuestion.getDescription());
        return questionRepository.save(question);
    }

    public Question voteOnQuestion(Long id, String vote) {
        Question question = getQuestionById(id);
        Vote voteDir = Vote.valueOf(vote);
        question.setNumberOfVotes(question.getNumberOfVotes() + voteDir.getValue());
        return questionRepository.save(question);
    }
}
