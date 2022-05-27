package com.codecool.Forum.service;

import com.codecool.Forum.exception.CommentNotFoundException;
import com.codecool.Forum.mapper.CommentDtoMapper;
import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.dto.AnswerGetDto;
import com.codecool.Forum.model.dto.CommentGetDto;
import com.codecool.Forum.model.dto.CommentPostDto;
import com.codecool.Forum.model.dto.QuestionGetDto;
import com.codecool.Forum.reporsitory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDtoMapper commentDtoMapper;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          CommentDtoMapper commentDtoMapper,
                          QuestionService questionService,
                          AnswerService answerService) {
        this.commentRepository = commentRepository;
        this.commentDtoMapper = commentDtoMapper;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public CommentGetDto getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return commentDtoMapper.commentToCommentGetDto(comment.get());
        }
        throw new CommentNotFoundException(id);
    }

    public List<CommentGetDto> getCommentsByQuestionId(Long questionId) {
        return commentRepository.findCommentsByQuestionId(questionId)
                .stream().map(commentDtoMapper::commentToCommentGetDto).collect(Collectors.toList());
    }

    public List<CommentGetDto> getCommentsByAnswerId(Long answerId) {
        return commentRepository.findCommentsByAnswerId(answerId)
                .stream().map(commentDtoMapper::commentToCommentGetDto).collect(Collectors.toList());
    }

    public CommentGetDto addCommentToQuestion(Long questionId, CommentPostDto commentPostDto) {
        QuestionGetDto questionGetDto = questionService.getQuestionById(questionId);
        commentPostDto.setQuestionGetDto(questionGetDto);
        return commentDtoMapper.commentToCommentGetDto(
                commentRepository.save(commentDtoMapper.commentPostDtoToComment(commentPostDto)));
    }

    public CommentGetDto addCommentToAnswer(Long answerId, CommentPostDto commentPostDto) {
        AnswerGetDto answerGetDto = answerService.getAnswerById(answerId);
        commentPostDto.setAnswerGetDto(answerGetDto);
        return commentDtoMapper.commentToCommentGetDto(
                commentRepository.save(commentDtoMapper.commentPostDtoToComment(commentPostDto)));
    }

    public CommentGetDto update(Long id, CommentPostDto commentPostDto) {
        CommentGetDto commentGetDto = getCommentById(id);
        commentGetDto.setMessage(commentPostDto.getMessage());
        commentGetDto.setEditedCount(commentGetDto.getEditedCount() + 1);
        commentGetDto.setSubmissionTime(LocalDateTime.now());
        return commentDtoMapper.commentToCommentGetDto(
                commentRepository.save(commentDtoMapper.commentGetDtoToComment(commentGetDto)));
    }

    public void delete(Long commentId) {
        CommentGetDto commentGetDto = getCommentById(commentId);
        commentRepository.delete(commentDtoMapper.commentGetDtoToComment(commentGetDto));
    }
}
