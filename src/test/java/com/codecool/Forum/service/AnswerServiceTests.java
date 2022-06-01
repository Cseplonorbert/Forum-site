package com.codecool.Forum.service;

import com.codecool.Forum.mapper.AnswerDtoMapper;
import com.codecool.Forum.reporsitory.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class AnswerServiceTests {

    @Mock
    AnswerRepository answerRepository;

    @Mock
    AnswerDtoMapper answerDtoMapper;

    @Mock
    QuestionService questionService;

    @InjectMocks
    AnswerService answerService;

    @DisplayName("Get answer by id return AnswerGetDto")
    @Test
    public void givenAnswerId_whenGetAnswerById_thenReturnAnswerGetDtoObject(){

    }

    @DisplayName("Get answer by non-exists id throw AnswerNotFoundException")
    @Test
    public void givenNonExistAnswerId_whenGetAnswerById_thenThrowAnswerNotFoundException(){

    }

    @DisplayName("Get answers by questionId return a list of AnswerGetDto")
    @Test
    public void givenQuestionId_whenGetAnswersByQuestion_thenReturnListOfAnswerGetDtoObject(){

    }

    @DisplayName("Get answer by questionId throw QuestionNotFoundException")
    @Test
    public void givenNonExistQuestionId_wenGetAnswersByQuestion_thenThrowQuestionNotFoundException(){

    }

    @DisplayName("Add answer")
    @Test
    public void givenAnswerPostDto_whenAdd_thenReturnAnswerGetDtoObject(){

    }

    @DisplayName("Update answer")
    @Test
    public void givenAnswerPostDto_whenUpdate_thenReturnAnswerGetDtoObject(){

    }

    @DisplayName("Delete answer")
    @Test
    public void givenAnswerId_whenDelete_thenNothing(){

    }

    @DisplayName("Up vote answer")
    @Test
    public void givenAnswerId_whenUpVote_thenReturnAnswerGetDtoObject(){

    }

    @DisplayName("Down vote answer")
    @Test
    public void givenAnswerId_whenDownVote_thenReturnAnswerGetDtoObject(){

    }
}
