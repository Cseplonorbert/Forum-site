package com.codecool.Forum.service;

import com.codecool.Forum.mapper.CommentDtoMapper;
import com.codecool.Forum.reporsitory.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class CommentServiceTests {

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentDtoMapper commentDtoMapper;

    @Mock
    AnswerService answerService;

    @Mock
    QuestionService questionService;

    @InjectMocks
    CommentService commentService;


    @DisplayName("Get comment by id return CommentGetDto")
    @Test
    public void givenCommentId_whenGetCommentById_thenReturnCommentGetDtoObject(){

    }

    @DisplayName("Get comment by id throw CommentNotFoundException")
    @Test
    public void givenNonExistCommentId_whenGetCommentById_thenThrowCommentNotFoundException(){

    }

    @DisplayName("Get comments by question id return list of CommentGetDto")
    @Test
    public void givenQuestionId_whenGetCommentsByQuestionId_thenReturnListOfCommentGetDtoObject(){

    }

    @DisplayName("Get comments by question id throw QuestionNotFoundException")
    @Test
    public void givenNonExistQuestionId_whenGetCommentsByQuestionId_thenThrowQuestionNotFoundException(){

    }

    @DisplayName("Get comments by answer id return list of CommentGetDto")
    @Test
    public void givenAnswerId_whenGetCommentsByAnswerId_thenReturnListOfCommentGetDtoObject(){

    }

    @DisplayName("Get comments by answer id throw AnswerNotFoundException")
    @Test
    public void givenNonExistAnswerId_whenGetCommentsByAnswerId_thenThrowAnswerNotFoundException(){

    }

    @DisplayName("Add comment to question")
    @Test
    public void givenCommentPostDto_whenAddCommentToQuestion_thenReturnCommentGetDtoObject(){

    }

    @DisplayName("Add comment to answer")
    @Test
    public void givenCommentPostDto_whenAddCommentToAnswer_thenReturnCommentGetDtoObject(){

    }

    @DisplayName("Edit comment")
    @Test
    public void givenCommentPostDto_whenUpdate_thenReturnCommentGetDtoObject(){

    }

    @DisplayName("Delete comment")
    @Test
    public void givenCommentId_whenDelete_thenNothing() {

    }
}
