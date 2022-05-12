package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.QuestionPreviewAssembler;
import com.codecool.Forum.assembler.QuestionViewAssembler;
import com.codecool.Forum.exception.*;
import com.codecool.Forum.model.Answer;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    QuestionService questionService;
    TagService tagService;
    QuestionPreviewAssembler questionPreviewAssembler;
    QuestionViewAssembler questionViewAssembler;

    @Autowired
    public QuestionController(QuestionService questionService,
                              TagService tagService,
                              QuestionPreviewAssembler questionPreviewAssembler,
                              QuestionViewAssembler questionViewAssembler) {
        this.questionService = questionService;
        this.tagService = tagService;
        this.questionPreviewAssembler = questionPreviewAssembler;
        this.questionViewAssembler = questionViewAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<QuestionPreview>> all(
                                                        @RequestParam(defaultValue = "SUBMISSION_TIME") String sort,
                                                        @RequestParam(defaultValue = "DESC") String order,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "15") Integer pageSize) {
        try {
            Page<Question> questions = questionService.findAll(order, sort, page, pageSize);
            return CollectionModel.of(questions.map(question -> questionPreviewAssembler.toModel(question)),
                    linkTo(methodOn(QuestionController.class).all(sort, order, page, pageSize)).withSelfRel());
        } catch (IllegalArgumentException | IllegalPageSizeException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @GetMapping("/search")
    public  CollectionModel<EntityModel<QuestionPreview>> search(
                                                        @RequestParam(defaultValue = "SUBMISSION_TIME") String sort,
                                                        @RequestParam(defaultValue = "DESC") String order,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "15") Integer pageSize,
                                                        @RequestParam String phrase) {
        try {
            Page<Question> questions = questionService.search(phrase, order, sort, page, pageSize);
            return CollectionModel.of(questions.map(question -> questionPreviewAssembler.toModel(question)),
                    linkTo(methodOn(QuestionController.class).search(sort, order, page, pageSize, phrase))
                            .withSelfRel());
        } catch (IllegalArgumentException | IllegalPageSizeException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @GetMapping("/{id}")
    public EntityModel<QuestionView> getQuestionById(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return questionViewAssembler.toModel(question);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question Not Found", exc
            );
        }
    }

    @PostMapping("/add")
    public void add(@RequestParam String title, @RequestParam String description) {

    }

    @PutMapping("/{id}/edit")
    public void edit(@PathVariable Long id,@RequestParam String title, @RequestParam String description) {

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
