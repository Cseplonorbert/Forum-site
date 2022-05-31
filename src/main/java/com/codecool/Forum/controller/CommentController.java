package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.CommentAssembler;
import com.codecool.Forum.model.dto.CommentGetDto;
import com.codecool.Forum.model.dto.CommentPostDto;
import com.codecool.Forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final CommentAssembler commentViewAssembler;

    @Autowired
    public CommentController(CommentService commentService,
                             CommentAssembler commentViewAssembler) {
        this.commentService = commentService;
        this.commentViewAssembler = commentViewAssembler;
    }

    @GetMapping("/questions/{questionId}/comments")
    public CollectionModel<EntityModel<CommentGetDto>> getAllByQuestionId(@PathVariable Long questionId) {
        return commentViewAssembler.toCollectionModel(commentService.getCommentsByQuestionId(questionId));
    }

    @PostMapping("/questions/{questionId}/comments")
    public EntityModel<CommentGetDto> addCommentToQuestion(@PathVariable Long questionId,
                                                           @Valid @RequestBody CommentPostDto commentPostDto) {
        return commentViewAssembler.toModel(commentService.addCommentToQuestion(questionId, commentPostDto));
    }

    @GetMapping("/answers/{answerId}/comments")
    public CollectionModel<EntityModel<CommentGetDto>> getAllByAnswerId(@PathVariable Long answerId) {
        return commentViewAssembler.toCollectionModel(commentService.getCommentsByAnswerId(answerId));
    }

    @PostMapping("/answers/{answerId}/comments")
    public EntityModel<CommentGetDto> addCommentToAnswer(@PathVariable Long answerId,
                                                         @Valid @RequestBody CommentPostDto commentPostDto) {
        return commentViewAssembler.toModel(commentService.addCommentToAnswer(answerId, commentPostDto));
    }

    @GetMapping("/comments/{commentId}")
    public EntityModel<CommentGetDto> get(@PathVariable Long commentId) {
        return commentViewAssembler.toModel(commentService.getCommentById(commentId));
    }

    @PutMapping("/comments/{commentId}")
    public EntityModel<CommentGetDto> update(@PathVariable Long commentId,
                                                            @Valid @RequestBody CommentPostDto commentPostDto) {
        return commentViewAssembler.toModel(commentService.update(commentId, commentPostDto));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
