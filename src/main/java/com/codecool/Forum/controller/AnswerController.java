package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.AnswerViewAssembler;
import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.view.AnswerView;
import com.codecool.Forum.service.AnswerService;
import com.codecool.Forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final AnswerViewAssembler answerViewAssembler;

    @Autowired
    public AnswerController (AnswerService answerService,
                             QuestionService questionService,
                             AnswerViewAssembler answerViewAssembler) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.answerViewAssembler = answerViewAssembler;
    }

    @GetMapping("/questions/{questionId}/answers")
    public CollectionModel<EntityModel<AnswerView>> getAllAnswersByQuestionId(@PathVariable Long questionId) {
        if (!questionService.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        return answerViewAssembler.toCollectionModel(answerService.getAnswersByQuestionId(questionId));
    }

    @PostMapping("/questions/{questionId}/answers")
    public CollectionModel<EntityModel<AnswerView>> createAnswer(@PathVariable Long questionId,
                                                                 @RequestBody Answer answer ) {
        Question question = questionService.getQuestionById(questionId);
        answerService.add(question, answer);
        return getAllAnswersByQuestionId(questionId);
    }

    @GetMapping("/answers/{answerId}")
    public EntityModel<AnswerView> getAnswerById(@PathVariable Long answerId) {
        return answerViewAssembler.toModel(answerService.getAnswerById(answerId));
    }

    @DeleteMapping("/answers/{answerId}")
    public CollectionModel<EntityModel<AnswerView>> deleteAnswer(@PathVariable Long answerId) {
        Question question = answerService.deleteAnswer(answerId);
        return getAllAnswersByQuestionId(question.getId());
    }

    @PutMapping("/answers/{answerId}")
    public CollectionModel<EntityModel<AnswerView>> update(@PathVariable Long answerId,
                                                           @RequestParam Answer answer) {
        Question question = answerService.update(answerId, answer);
        return getAllAnswersByQuestionId(question.getId());
    }

    @PostMapping("/answers/{answerId}/up_vote")
    public CollectionModel<EntityModel<AnswerView>> upVote(@PathVariable Long answerId) {
        Question question = answerService.upVote(answerId);
        return getAllAnswersByQuestionId(question.getId());
    }

    @PostMapping("/answers/{answerId}/down_vote")
    public CollectionModel<EntityModel<AnswerView>> downVote(@PathVariable Long answerId) {
        Question question = answerService.downVote(answerId);
        return getAllAnswersByQuestionId(question.getId());
    }
}
