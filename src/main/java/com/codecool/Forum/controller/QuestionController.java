package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.*;
import com.codecool.Forum.exception.*;
import com.codecool.Forum.model.*;
import com.codecool.Forum.model.view.*;
import com.codecool.Forum.service.CommentService;
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
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;
    private final TagService tagService;
    private final QuestionPreviewAssembler questionPreviewAssembler;
    private final QuestionViewAssembler questionViewAssembler;
    private final PagedResourcesAssembler<Question> pagedResourcesAssembler;
    private final CommentService commentService;
    private final CommentViewAssembler commentViewAssembler;
    private final TagViewAssembler tagViewAssembler;

    @Autowired
    public QuestionController(QuestionService questionService,
                              TagService tagService,
                              QuestionPreviewAssembler questionPreviewAssembler,
                              QuestionViewAssembler questionViewAssembler,
                              PagedResourcesAssembler<Question> pagedResourcesAssembler,
                              CommentService commentService,
                              CommentViewAssembler commentViewAssembler,
                              TagViewAssembler tagViewAssembler) {
        this.questionService = questionService;
        this.tagService = tagService;
        this.questionPreviewAssembler = questionPreviewAssembler;
        this.questionViewAssembler = questionViewAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.commentService = commentService;
        this.commentViewAssembler = commentViewAssembler;
        this.tagViewAssembler = tagViewAssembler;
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

    @PutMapping("/questions/{id}/edit")
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

    @GetMapping("/questions/{id}/comments")
    public CollectionModel<EntityModel<CommentView>> getComments(@PathVariable Long id) {
        return commentViewAssembler.toCollectionModel(commentService.getCommentsByQuestionId(id));
    }

    @PostMapping("/questions/{id}/comments/add")
    public ResponseEntity<EntityModel<CommentView>> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        Question question = questionService.getQuestionById(id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentViewAssembler.toModel(commentService.add(question, comment)));
    }

    @GetMapping("/questions/{id}tags")
    public CollectionModel<EntityModel<TagView>> getTags(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        return tagViewAssembler.toCollectionModel(tagService.getTagsByQuestion(question));
    }

    @PostMapping("/questions/{id}/tags/add")
    public EntityModel<TagView> addTag(@PathVariable Long id, @RequestParam String tagName) {
        try {
            Question question = questionService.getQuestionById(id);
            return tagViewAssembler.toModel(tagService.add(tagName, question));
        } catch (TagAlreadyAddedToQuestionException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @DeleteMapping("/questions/{id}/tags/delete/{tag_id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id, @PathVariable Long tag_id) {
        try {
            Question question = questionService.getQuestionById(id);
            Tag tag = tagService.getTagById(tag_id);
            tagService.removeTagFromQuestion(tag, question);
            return ResponseEntity.noContent().build();
        } catch (TagNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        } catch (TagNotBeenAddedToQuestionException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }
}
