package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.*;
import com.codecool.Forum.exception.*;
import com.codecool.Forum.model.*;
import com.codecool.Forum.model.view.*;
import com.codecool.Forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionPreviewAssembler questionPreviewAssembler;
    private final QuestionViewAssembler questionViewAssembler;
    private final PagedResourcesAssembler<Question> pagedResourcesAssembler;

    @Autowired
    public QuestionController(QuestionService questionService,
                              QuestionPreviewAssembler questionPreviewAssembler,
                              QuestionViewAssembler questionViewAssembler,
                              PagedResourcesAssembler<Question> pagedResourcesAssembler) {
        this.questionService = questionService;
        this.questionPreviewAssembler = questionPreviewAssembler;
        this.questionViewAssembler = questionViewAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/questions")
    public PagedModel<EntityModel<QuestionPreview>> all(
                                                        @RequestParam(defaultValue = "SUBMISSION_TIME") String sort,
                                                        @RequestParam(defaultValue = "DESC") String order,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "15") Integer pageSize) {
        try {
            Page<Question> questions = questionService.findAll(order, sort, page, pageSize);
            return pagedResourcesAssembler.toModel(questions, questionPreviewAssembler);
        } catch (IllegalArgumentException | IllegalPageSizeException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @GetMapping("/questions/search")
    public  PagedModel<EntityModel<QuestionPreview>> search(
                                                        @RequestParam(defaultValue = "SUBMISSION_TIME") String sort,
                                                        @RequestParam(defaultValue = "DESC") String order,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "15") Integer pageSize,
                                                        @RequestParam String phrase) {
        try {
            Page<Question> questions = questionService.search(phrase, order, sort, page, pageSize);
            return pagedResourcesAssembler.toModel(questions, questionPreviewAssembler);
        } catch (IllegalArgumentException | IllegalPageSizeException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @GetMapping("/questions/{id}")
    public EntityModel<QuestionView> get(@PathVariable Long id) {
        return questionViewAssembler.toModel(questionService.getQuestionById(id));
    }

    @PostMapping("/questions/add")
    public ResponseEntity<EntityModel<QuestionView>> add(@RequestBody Question question) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(questionViewAssembler
                        .toModel(questionService.add(question)));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<EntityModel<QuestionView>> update(@PathVariable Long id, Question question) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questionViewAssembler
                        .toModel(questionService.update(id, question)));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{id}/down_vote")
    public EntityModel<QuestionView> downVote(@PathVariable Long id) {
        return questionViewAssembler.toModel(questionService.downVote(id));
    }

    @PostMapping("/questions/{id}/up_vote")
    public EntityModel<QuestionView> upVote(@PathVariable Long id) {
        return questionViewAssembler.toModel(questionService.upVote(id));
    }
}
