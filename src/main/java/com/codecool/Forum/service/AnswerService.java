package com.codecool.Forum.service;

import com.codecool.Forum.exception.AnswerNotFoundException;
import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.mapper.AnswerDtoMapper;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Vote;
import com.codecool.Forum.model.dto.AnswerGetDto;
import com.codecool.Forum.model.dto.AnswerPostDto;
import com.codecool.Forum.model.dto.QuestionGetDto;
import com.codecool.Forum.reporsitory.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final AnswerDtoMapper answerDtoMapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository,
                         QuestionService questionService,
                         AnswerDtoMapper answerDtoMapper) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.answerDtoMapper = answerDtoMapper;
    }

    public boolean existsById(Long id) {
        return answerRepository.existsById(id);
    }

    public AnswerGetDto add(Long questionId, AnswerPostDto answerPostDto) {
        QuestionGetDto questionGetDto = questionService.getQuestionById(questionId);
        answerPostDto.setQuestionGetDto(questionGetDto);
        Answer answer = answerRepository.save(answerDtoMapper.answerPostDtoToAnswer(answerPostDto));
        return answerDtoMapper.answerToAnswerGetDto(answer);
    }

    public List<AnswerGetDto> getAnswersByQuestionId(Long questionId) {
        if (!questionService.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        return answerRepository.findAnswersByQuestionId(questionId)
                .stream().map(answerDtoMapper::answerToAnswerGetDto).collect(Collectors.toList());
    }

    public AnswerGetDto getAnswerById(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (answer.isPresent()) {
            return answerDtoMapper.answerToAnswerGetDto(answer.get());
        }
        throw new AnswerNotFoundException(answerId);
    }

    public void deleteAnswer(Long answerId) {
        AnswerGetDto answerGetDto = getAnswerById(answerId);
        answerRepository.delete(answerDtoMapper.answerGetDtoToAnswer(answerGetDto));
    }

    public AnswerGetDto update(Long answerId, AnswerPostDto answerPostDto) {
        AnswerGetDto answerGetDto = getAnswerById(answerId);
        answerGetDto.setMessage(answerPostDto.getMessage());
        return answerDtoMapper.answerToAnswerGetDto(
                answerRepository.save(answerDtoMapper.answerGetDtoToAnswer(answerGetDto)));
    }

    public AnswerGetDto upVote(Long answerId) {
        AnswerGetDto answerGetDto = getAnswerById(answerId);
        Vote vote = Vote.UP;
        return vote(answerGetDto, vote);
    }

    public AnswerGetDto downVote(Long answerId) {
        AnswerGetDto answerGetDto = getAnswerById(answerId);
        Vote vote = Vote.DOWN;
        return vote(answerGetDto, vote);
    }

    private AnswerGetDto vote(AnswerGetDto answerGetDto, Vote vote) {
        answerGetDto.setVoteNumber(answerGetDto.getVoteNumber() + vote.getValue());
        return answerDtoMapper.answerToAnswerGetDto(
                answerRepository.save(answerDtoMapper.answerGetDtoToAnswer(answerGetDto)));
    }
}
