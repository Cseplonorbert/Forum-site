package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.*;
import com.codecool.Forum.exception.*;
import com.codecool.Forum.model.*;
import com.codecool.Forum.model.view.AnswerView;
import com.codecool.Forum.model.view.CommentView;
import com.codecool.Forum.model.view.QuestionView;
import com.codecool.Forum.model.view.TagView;
import com.codecool.Forum.service.AnswerService;
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
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final TagService tagService;
    private final QuestionPreviewAssembler questionPreviewAssembler;
    private final QuestionViewAssembler questionViewAssembler;
    private final PagedResourcesAssembler<Question> pagedResourcesAssembler;
    private final AnswerViewAssembler answerViewAssembler;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final CommentViewAssembler commentViewAssembler;
    private final TagViewAssembler tagViewAssembler;

    @Autowired
    public QuestionController(QuestionService questionService,
                              TagService tagService,
                              QuestionPreviewAssembler questionPreviewAssembler,
                              QuestionViewAssembler questionViewAssembler,
                              PagedResourcesAssembler<Question> pagedResourcesAssembler,
                              AnswerViewAssembler answerViewAssembler,
                              AnswerService answerService,
                              CommentService commentService,
                              CommentViewAssembler commentViewAssembler,
                              TagViewAssembler tagViewAssembler) {
        this.questionService = questionService;
        this.tagService = tagService;
        this.questionPreviewAssembler = questionPreviewAssembler;
        this.questionViewAssembler = questionViewAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.answerViewAssembler = answerViewAssembler;
        this.answerService = answerService;
        this.commentService = commentService;
        this.commentViewAssembler = commentViewAssembler;
        this.tagViewAssembler = tagViewAssembler;
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            questionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PostMapping("/{id}/down_vote")
    public EntityModel<QuestionView> downVote(@PathVariable Long id) {
        try {
            return questionViewAssembler.toModel(questionService.downVote(id));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PostMapping("/{id}/up_vote")
    public EntityModel<QuestionView> upVote(@PathVariable Long id) {
        try {
            return questionViewAssembler.toModel(questionService.upVote(id));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @GetMapping("/{id}/answers")
    public CollectionModel<EntityModel<AnswerView>> getAnswers(@PathVariable Long id) {
        return answerViewAssembler.toCollectionModel(answerService.getAnswersByQuestionId(id));
    }

    @PostMapping("/{id}/answers/add")
    public ResponseEntity<EntityModel<AnswerView>> addAnswer(@PathVariable Long id, @RequestBody Answer answer) {
        try {
            Question question = questionService.getQuestionById(id);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(answerViewAssembler
                            .toModel(answerService.add(question, answer)));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @GetMapping("/{id}/comments")
    public CollectionModel<EntityModel<CommentView>> getComments(@PathVariable Long id) {
        return commentViewAssembler.toCollectionModel(commentService.getCommentsByQuestionId(id));
    }

    @PostMapping("/{id}/comments/add")
    public ResponseEntity<EntityModel<CommentView>> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            Question question = questionService.getQuestionById(id);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(commentViewAssembler.toModel(commentService.add(question, comment)));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @GetMapping("/{id}tags")
    public CollectionModel<EntityModel<TagView>> getTags(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return tagViewAssembler.toCollectionModel(tagService.getTagsByQuestion(question));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PostMapping("/{id}/tags/add")
    public EntityModel<TagView> addTag(@PathVariable Long id, @RequestParam String tagName) {
        try {
            Question question = questionService.getQuestionById(id);
            return tagViewAssembler.toModel(tagService.add(tagName, question));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        } catch (TagAlreadyAddedToQuestionException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @DeleteMapping("/{id}/tags/delete/{tag_id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id, @PathVariable Long tag_id) {
        try {
            Question question = questionService.getQuestionById(id);
            Tag tag = tagService.getTagById(tag_id);
            tagService.removeTagFromQuestion(tag, question);
            return ResponseEntity.noContent().build();
        } catch (QuestionNotFoundException | TagNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        } catch (TagNotBeenAddedToQuestionException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }
}
