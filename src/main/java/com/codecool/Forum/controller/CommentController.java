package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.CommentViewAssembler;
import com.codecool.Forum.exception.AnswerNotFoundException;
import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.view.CommentView;
import com.codecool.Forum.service.AnswerService;
import com.codecool.Forum.service.CommentService;
import com.codecool.Forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final CommentViewAssembler commentViewAssembler;

    @Autowired
    public CommentController(CommentService commentService,
                             AnswerService answerService,
                             QuestionService questionService,
                             CommentViewAssembler commentViewAssembler) {
        this.commentService = commentService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.commentViewAssembler = commentViewAssembler;
    }

    @GetMapping("/questions/{questionId}/comments")
    public CollectionModel<EntityModel<CommentView>> getAllByQuestionId(@PathVariable Long questionId) {
        if (!questionService.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        return commentViewAssembler.toCollectionModel(commentService.getCommentsByQuestionId(questionId));
    }

    @PostMapping("/questions/{questionId}/comments")
    public CollectionModel<EntityModel<CommentView>> addCommentToQuestion(@PathVariable Long questionId,
                                                                             @RequestBody Comment comment) {
        Question question = questionService.getQuestionById(questionId);
        commentService.add(question, comment);
        return getAllByQuestionId(questionId);
    }

    @GetMapping("/answers/{answerId}/comments")
    public CollectionModel<EntityModel<CommentView>> getAllByAnswerId(@PathVariable Long answerId) {
        if(!answerService.existsById(answerId)) {
            throw new AnswerNotFoundException(answerId);
        }
        return commentViewAssembler.toCollectionModel(commentService.getCommentsByAnswerId(answerId));
    }

    @PostMapping("/answers/{answerId}/comments")
    public CollectionModel<EntityModel<CommentView>> addCommentToAnswer(@PathVariable Long answerId,
                                                                             @RequestBody Comment comment) {
        Answer answer = answerService.getAnswerById(answerId);
        commentService.add(answer, comment);
        return getAllByAnswerId(answerId);
    }

    @GetMapping("/comments/{commentId}")
    public EntityModel<CommentView> get(@PathVariable Long commentId) {
        return commentViewAssembler.toModel(commentService.getCommentById(commentId));
    }

    @PutMapping("/comments/{commentId}")
    public CollectionModel<EntityModel<CommentView>> update(@PathVariable Long commentId,
                                                            @RequestParam Comment comment) {
        comment = commentService.update(commentId, comment);
        if (!commentService.isBoundedToQuestion(comment)) {
            return getAllByAnswerId(comment.getAnswer().getId());
        }
        return getAllByQuestionId(comment.getQuestion().getId());
    }

    @DeleteMapping("/comments/{commentId}")
    public CollectionModel<EntityModel<CommentView>> delete(@PathVariable Long commentId) {
        Comment comment = commentService.delete(commentId);
        if (!commentService.isBoundedToQuestion(comment)) {
            return getAllByAnswerId(comment.getAnswer().getId());
        }
        return getAllByQuestionId(comment.getQuestion().getId());
    }
}
