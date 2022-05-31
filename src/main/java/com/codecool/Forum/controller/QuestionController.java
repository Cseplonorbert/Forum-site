package com.codecool.Forum.controller;

import static com.codecool.Forum.controller.constants.QuestionControllerConstants.*;

import com.codecool.Forum.assembler.*;
import com.codecool.Forum.model.dto.QuestionGetDto;
import com.codecool.Forum.model.dto.QuestionPostDto;
import com.codecool.Forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionAssembler questionAssembler;
    private final PagedResourcesAssembler<QuestionGetDto> pagedResourcesAssembler;

    @Autowired
    public QuestionController(QuestionService questionService,
                              QuestionAssembler questionViewAssembler,
                              PagedResourcesAssembler<QuestionGetDto> pagedResourcesAssembler) {
        this.questionService = questionService;
        this.questionAssembler = questionViewAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/questions")
    public PagedModel<EntityModel<QuestionGetDto>> all(
                                                @RequestParam(defaultValue = DEFAULT_SORT) String sort,
                                                @RequestParam(defaultValue = DEFAULT_ORDER) String order,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
                                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
            Page<QuestionGetDto> questions = questionService.findAll(order, sort, page, pageSize);
            return pagedResourcesAssembler.toModel(questions, questionAssembler);
    }

    @GetMapping("/questions/search")
    public  PagedModel<EntityModel<QuestionGetDto>> search(
                                                @RequestParam(defaultValue = DEFAULT_SORT) String sort,
                                                @RequestParam(defaultValue = DEFAULT_ORDER) String order,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
                                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                                @RequestParam String phrase) {
            Page<QuestionGetDto> questions = questionService.search(phrase, order, sort, page, pageSize);
            return pagedResourcesAssembler.toModel(questions, questionAssembler);
    }

    @GetMapping("/questions/{id}")
    public EntityModel<QuestionGetDto> get(@PathVariable Long id) {
        return questionAssembler.toModel(questionService.getQuestionById(id));
    }

    @PostMapping("/questions/add")
    public ResponseEntity<EntityModel<QuestionGetDto>> add(@Valid @RequestBody QuestionPostDto questionPostDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(questionAssembler
                        .toModel(questionService.add(questionPostDto)));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<EntityModel<QuestionGetDto>> update(@PathVariable Long id,
                                                              @Valid @RequestBody QuestionPostDto questionPostDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questionAssembler
                        .toModel(questionService.update(id, questionPostDto)));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{id}/down_vote")
    public EntityModel<QuestionGetDto> downVote(@PathVariable Long id) {
        return questionAssembler.toModel(questionService.downVote(id));
    }

    @PostMapping("/questions/{id}/up_vote")
    public EntityModel<QuestionGetDto> upVote(@PathVariable Long id) {
        return questionAssembler.toModel(questionService.upVote(id));
    }
}
