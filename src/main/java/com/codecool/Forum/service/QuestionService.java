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
                findByTitleContainingIgnoreCaseOrMessageContainingIgnoreCase(pageable, phrase, phrase);
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
                .message(question.getMessage())
                .build());
    }

    public void delete(Long id) {
        Question question = getQuestionById(id);
        questionRepository.delete(question);
    }

    public Question update(Long id, Question editedQuestion) {
        Question question = getQuestionById(id);
        question.setTitle(editedQuestion.getTitle());
        question.setMessage(editedQuestion.getMessage());
        return questionRepository.save(question);
    }

    public Question downVote(Long id) {
        Vote vote = Vote.DOWN;
        return vote(vote, id);
    }

    public Question upVote(Long id) {
        Vote vote = Vote.UP;
        return vote(vote, id);
    }

    private Question vote(Vote vote, Long id) {
        Question question = getQuestionById(id);
        question.setVoteNumber(question.getVoteNumber() + vote.getValue());
        return questionRepository.save(question);
    }
}
