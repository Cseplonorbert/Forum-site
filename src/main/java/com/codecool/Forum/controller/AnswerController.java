package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.AnswerAssembler;
import com.codecool.Forum.model.dto.AnswerGetDto;
import com.codecool.Forum.model.dto.AnswerPostDto;
import com.codecool.Forum.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerAssembler answerViewAssembler;

    @Autowired
    public AnswerController (AnswerService answerService,
                             AnswerAssembler answerViewAssembler) {
        this.answerService = answerService;
        this.answerViewAssembler = answerViewAssembler;
    }

    @GetMapping("/questions/{questionId}/answers")
    public CollectionModel<EntityModel<AnswerGetDto>> getAllAnswersByQuestionId(@PathVariable Long questionId) {
        return answerViewAssembler.toCollectionModel(answerService.getAnswersByQuestionId(questionId));
    }

    @PostMapping("/questions/{questionId}/answers")
    public EntityModel<AnswerGetDto> createAnswer(@PathVariable Long questionId,
                                                                 @RequestBody AnswerPostDto answerPostDto ) {
        return answerViewAssembler.toModel(answerService.add(questionId, answerPostDto));
    }

    @GetMapping("/answers/{answerId}")
    public EntityModel<AnswerGetDto> getAnswerById(@PathVariable Long answerId) {
        return answerViewAssembler.toModel(answerService.getAnswerById(answerId));
    }

    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/answers/{answerId}")
    public EntityModel<AnswerGetDto> update(@PathVariable Long answerId,
                                                           @RequestBody AnswerPostDto answerPostDto) {
        return answerViewAssembler.toModel(answerService.update(answerId, answerPostDto));
    }

    @PostMapping("/answers/{answerId}/up_vote")
    public EntityModel<AnswerGetDto> upVote(@PathVariable Long answerId) {
        return answerViewAssembler.toModel(answerService.upVote(answerId));
    }

    @PostMapping("/answers/{answerId}/down_vote")
    public EntityModel<AnswerGetDto> downVote(@PathVariable Long answerId) {
        return answerViewAssembler.toModel(answerService.downVote(answerId));
    }
}
