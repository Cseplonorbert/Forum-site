package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.QuestionPreviewAssembler;
import com.codecool.Forum.assembler.QuestionViewAssembler;
import com.codecool.Forum.exception.*;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.QuestionPreview;
import com.codecool.Forum.model.view.AnswerView;
import com.codecool.Forum.model.view.CommentView;
import com.codecool.Forum.model.view.QuestionView;
import com.codecool.Forum.model.view.TagView;
import com.codecool.Forum.service.QuestionService;
import com.codecool.Forum.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final TagService tagService;
    private final QuestionPreviewAssembler questionPreviewAssembler;
    private final QuestionViewAssembler questionViewAssembler;
    private final PagedResourcesAssembler<Question> pagedResourcesAssembler;

    @Autowired
    public QuestionController(QuestionService questionService,
                              TagService tagService,
                              QuestionPreviewAssembler questionPreviewAssembler,
                              QuestionViewAssembler questionViewAssembler,
                              PagedResourcesAssembler<Question> pagedResourcesAssembler) {
        this.questionService = questionService;
        this.tagService = tagService;
        this.questionPreviewAssembler = questionPreviewAssembler;
        this.questionViewAssembler = questionViewAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
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

    @GetMapping("/search")
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

    @GetMapping("/{id}")
    public EntityModel<QuestionView> get(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return questionViewAssembler.toModel(question);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PostMapping("/add")
    public ResponseEntity<EntityModel<QuestionView>> add(@RequestBody Question question) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(questionViewAssembler
                            .toModel(questionService.add(question)));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<EntityModel<QuestionView>> update(@PathVariable Long id, Question question) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(questionViewAssembler
                            .toModel(questionService.update(id, question)));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }

    @PostMapping("/{id}/down_vote")
    public void downVote(@PathVariable Long id) {

    }

    @PostMapping("/{id}/up_vote")
    public void upVote(@PathVariable Long id) {

    }

    @GetMapping("/{id}/answers")
    public CollectionModel<EntityModel<AnswerView>> getAnswers(@PathVariable Long id) {
        return CollectionModel.empty();
    }

    @PostMapping("/{id}/answers/add")
    public void addAnswer(@PathVariable Long id, @RequestParam String message) {

    }

    @GetMapping("/{id}/comments")
    public CollectionModel<EntityModel<CommentView>> getComments(@PathVariable Long id) {
        return CollectionModel.empty();
    }

    @PostMapping("/{id}/comments/add")
    public void addComment(@PathVariable Long id, @RequestParam String message) {

    }

    @GetMapping("/{id}tags")
    public CollectionModel<EntityModel<TagView>> getTags(@PathVariable Long id) {
        return CollectionModel.empty();
    }

    @PostMapping("/{id}/tags/add")
    public void addTag(@PathVariable Long id, @RequestParam String tagName) {

    }

    @DeleteMapping("/{id}/tags/delete/{tag_id}")
    public void deleteTag(@PathVariable Long id, @PathVariable Long tag_id) {

    }
}
