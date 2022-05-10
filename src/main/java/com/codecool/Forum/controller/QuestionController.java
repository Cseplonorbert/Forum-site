package com.codecool.Forum.controller;

import com.codecool.Forum.exception.QuestionNotFoundException;
import com.codecool.Forum.exception.TagAlreadyAddedToQuestionException;
import com.codecool.Forum.exception.TagNotBeenAddedToQuestionException;
import com.codecool.Forum.exception.TagNotFoundException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Tag;
import com.codecool.Forum.service.AnswerService;
import com.codecool.Forum.service.CommentService;
import com.codecool.Forum.service.QuestionService;
import com.codecool.Forum.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/")
public class QuestionController {

    QuestionService questionService;
    AnswerService answerService;
    CommentService commentService;
    TagService tagService;

    @Autowired
    public QuestionController(QuestionService questionService,
                              AnswerService answerService,
                              CommentService commentService,
                              TagService tagService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.commentService = commentService;
        this.tagService = tagService;
    }

    @GetMapping("/list")
    public List<Question> getAllQuestions(@RequestParam(required = false, name = "order_by") String orderBy,
                                          @RequestParam(required = false, name = "order_direction") String orderDirection) {
        try {
            return questionService.getAllQuestions(orderBy, orderDirection);
        } catch (IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Sorting Query", exc);
        }
    }

    @GetMapping("/question/{question_id}")
    public Question getQuestionById(@PathVariable Long question_id) {
        try {
            return questionService.getQuestionById(question_id);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question Not Found", exc
            );
        }
    }

    @PostMapping("add-question")
    public ResponseEntity<Question> addQuestion(@RequestParam String title, @RequestParam String description) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.addQuestion(title, description));
    }

    @PostMapping("/question/{question_id}/new-answer")
    public ResponseEntity<Question> addAnswer(@RequestParam String message, @PathVariable Long question_id) {
        try {
            Question question = questionService.getQuestionById(question_id);
            answerService.addNewAnswer(question, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(question);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question Not Found", exc
            );
        }
    }

    @DeleteMapping("/question/{question_id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long question_id) {
        try {
            questionService.deleteQuestionById(question_id);
            return ResponseEntity.noContent().build();
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PutMapping("/question/{question_id}/edit")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long question_id, @RequestParam String title,
                                                   @RequestParam String description) {
        try {
            Question question = questionService.updateQuestion(question_id, title, description);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PutMapping("/question/{question_id}/vote")
    public ResponseEntity<Question> voteOnQuestion(@PathVariable Long question_id,
                                                     @RequestParam(name = "vote_method") String vote) {
        try {
            Question question = questionService.voteOnQuestion(question_id, vote);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (QuestionNotFoundException | IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PostMapping("/question/{question_id}/new-comment")
    public ResponseEntity<Question> addComment(@RequestParam String message,
                                               @PathVariable Long question_id) {
        try {
            Question question = questionService.getQuestionById(question_id);
            commentService.addNewComment(question, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(question);
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        }
    }

    @PostMapping("/question/{question_id}/new-tag")
    public ResponseEntity<Question> addTag(@PathVariable Long question_id,
                                           @RequestParam String tagName) {
        try {
            Question question = questionService.getQuestionById(question_id);
            tagService.add(tagName, question);
            return ResponseEntity.status(HttpStatus.CREATED).body(questionService.getQuestionById(question_id));
        } catch (QuestionNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        } catch (TagAlreadyAddedToQuestionException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc
            );
        }
    }

    @DeleteMapping("/question/{question_id}/tag/{tag_id}")
    public ResponseEntity<Question> removeTagFromQuestion(@PathVariable Long question_id,
                                                          @PathVariable Long tag_id) {
        try {
            Question question = questionService.getQuestionById(question_id);
            Tag tag = tagService.getTagById(tag_id);
            tagService.removeTagFromQuestion(tag, question);
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionById(question_id));
        } catch (QuestionNotFoundException | TagNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc
            );
        } catch (TagNotBeenAddedToQuestionException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc
            );
        }
    }
}
