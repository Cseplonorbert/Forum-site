package com.codecool.Forum.service;

import static com.codecool.Forum.service.constants.QuestionServiceConstants.*;

import com.codecool.Forum.exception.PageNotFoundException;
import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.model.SortBy;
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

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> findAll(String direction, String sortBy, Integer page, Integer pageSize) {
        Pageable pageable = getPageable(direction, sortBy, page, pageSize);
        Page<Question> results = questionRepository.findAll(pageable);
        if (results.getTotalPages() < pageable.getPageNumber()) {
            throw new PageNotFoundException(pageable.getPageNumber(), results.getTotalPages());
        }
        return results;
    }

    public Page<Question> search(String phrase, String direction, String sortBy, Integer page, Integer pageSize) {
        Pageable pageable = getPageable(direction, sortBy, page, pageSize);
        Page<Question> results = questionRepository
                .findByTitleContainingIgnoreCaseOrMessageContainingIgnoreCase(pageable, phrase, phrase);
        if (results.getTotalPages() < pageable.getPageNumber()) {
            throw new PageNotFoundException(pageable.getPageNumber(), results.getTotalPages());
        }
        return results;
    }

    private Pageable getPageable(String direction, String sortBy, Integer page, Integer pageSize) {
        if (pageSize > MAX_PAGE_SIZE || pageSize < MIN_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by(directionOfString(direction).orElse(DEFAULT_DIRECTION),
                orderByOfString(sortBy).orElse(DEFAULT_SORT_BY).getFieldName());

        return PageRequest.of(page, pageSize, sort);
    }

    private Optional<Sort.Direction> directionOfString(String direction) {
        try {
            return Optional.of(Sort.Direction.valueOf(direction));
        } catch (IllegalArgumentException exc) {
            return Optional.empty();
        }
    }

    private Optional<SortBy> orderByOfString(String sortBy) {
        try {
            return Optional.of(SortBy.valueOf(sortBy));
        } catch (IllegalArgumentException exc) {
            return Optional.empty();
        }
    }

    public boolean existsById(Long id) {
        return questionRepository.existsById(id);
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findQuestionById(id);
        if (question.isPresent()) {
            return question.get();
        }
        throw new QuestionNotFoundException(id);
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
