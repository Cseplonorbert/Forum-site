package com.codecool.Forum.controller;

import com.codecool.Forum.exception.CommentNotFoundException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PutMapping("/{comment_id}/edit")
    public ResponseEntity<Question> update(@PathVariable Long comment_id, @RequestParam String message) {
        try {
            Question question = commentService.update(comment_id,message);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (CommentNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @DeleteMapping("/{comment_id}/delete")
    public ResponseEntity<Question> delete(@PathVariable Long comment_id) {
        try {
            Question question = commentService.delete(comment_id);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (CommentNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }
}
