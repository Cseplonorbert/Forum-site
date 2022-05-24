package com.codecool.Forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Object> handleQuestionNotFoundException(QuestionNotFoundException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamps", LocalDateTime.now());
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<Object> handleAnswerNotFoundException(AnswerNotFoundException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamps", LocalDateTime.now());
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public  ResponseEntity<Object> handleCommentNotFoundException(CommentNotFoundException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamps", LocalDateTime.now());
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagAlreadyAddedToQuestionException.class)
    public ResponseEntity<Object> handleTagAlreadyAddedToQuestionException(TagAlreadyAddedToQuestionException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamps", LocalDateTime.now());
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TagNotBeenAddedToQuestionException.class)
    public ResponseEntity<Object> handleTagNotBeenAddedToQuestionException(TagNotBeenAddedToQuestionException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamps", LocalDateTime.now());
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Object> handleTagNotFoundException(TagNotFoundException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamps", LocalDateTime.now());
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
