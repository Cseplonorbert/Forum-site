package com.codecool.Forum.service;

import static com.codecool.Forum.service.constants.QuestionServiceConstants.*;

import com.codecool.Forum.exception.PageNotFoundException;
import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.mapper.QuestionDtoMapper;
import com.codecool.Forum.model.SortBy;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Vote;
import com.codecool.Forum.model.dto.QuestionGetDto;
import com.codecool.Forum.model.dto.QuestionPostDto;
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
    private final QuestionDtoMapper questionDtoMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           QuestionDtoMapper questionDtoMapper) {
        this.questionRepository = questionRepository;
        this.questionDtoMapper = questionDtoMapper;
    }

    public Page<QuestionGetDto> findAll(String direction, String sortBy, Integer page, Integer pageSize) {
        Pageable pageable = getPageable(direction, sortBy, page, pageSize);
        Page<Question> results = questionRepository.findAll(pageable);
        if (results.getTotalPages() < pageable.getPageNumber()) {
            throw new PageNotFoundException(pageable.getPageNumber(), results.getTotalPages());
        }
        return results.map(questionDtoMapper::questionToQuestionGetDto);
    }

    public Page<QuestionGetDto> search(String phrase, String direction, String sortBy, Integer page, Integer pageSize) {
        Pageable pageable = getPageable(direction, sortBy, page, pageSize);
        Page<Question> results = questionRepository
                .findByTitleContainingIgnoreCaseOrMessageContainingIgnoreCase(pageable, phrase, phrase);
        if (results.getTotalPages() < pageable.getPageNumber()) {
            throw new PageNotFoundException(pageable.getPageNumber(), results.getTotalPages());
        }
        return results.map(questionDtoMapper::questionToQuestionGetDto);
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

    public QuestionGetDto getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findQuestionById(id);
        if (question.isPresent()) {
            return questionDtoMapper.questionToQuestionGetDto(question.get());
        }
        throw new QuestionNotFoundException(id);
    }

    public QuestionGetDto add(QuestionPostDto questionPostDto) {
        Question question = questionRepository.save(questionDtoMapper.questionPostDtoToQuestion(questionPostDto));
        return questionDtoMapper.questionToQuestionGetDto(question);
    }

    public void delete(Long id) {
        QuestionGetDto questionGetDto = getQuestionById(id);
        questionRepository.delete(questionDtoMapper.questionGetDtoToQuestion(questionGetDto));
    }

    public QuestionGetDto update(Long id, QuestionPostDto questionPostDto) {
        QuestionGetDto questionGetDto = getQuestionById(id);
        questionGetDto.setTitle(questionPostDto.getTitle());
        questionGetDto.setMessage(questionPostDto.getMessage());
        Question question = questionRepository.save(questionDtoMapper.questionGetDtoToQuestion(questionGetDto));
        return questionDtoMapper.questionToQuestionGetDto(question);
    }

    public QuestionGetDto downVote(Long id) {
        Vote vote = Vote.DOWN;
        return vote(vote, id);
    }

    public QuestionGetDto upVote(Long id) {
        Vote vote = Vote.UP;
        return vote(vote, id);
    }

    private QuestionGetDto vote(Vote vote, Long id) {
        QuestionGetDto questionGetDto = getQuestionById(id);
        questionGetDto.setVoteNumber(questionGetDto.getVoteNumber() + vote.getValue());
        Question question = questionRepository.save(questionDtoMapper.questionGetDtoToQuestion(questionGetDto));
        return questionDtoMapper.questionToQuestionGetDto(question);
    }
}
