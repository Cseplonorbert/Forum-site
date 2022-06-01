package com.codecool.Forum.service;

import com.codecool.Forum.mapper.QuestionDtoMapper;
import com.codecool.Forum.reporsitory.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class QuestionServiceTests {

    @Mock
    QuestionRepository questionRepository;

    @Mock
    QuestionDtoMapper questionDtoMapper;

    @InjectMocks
    QuestionService questionService;

    @DisplayName("Get question by id returns QuestionGetDto")
    @Test
    public void givenQuestionId_whenGetQuestionById_thenReturnQuestionGetDtoObject(){

    }

    @DisplayName("Get question by non-exist id throws QuestionNotFoundException")
    @Test
    public void givenNonExistQuestionId_whenGetQuestionById_thenThrowQuestionNotFoundException(){

    }

    @DisplayName("Add new question")
    @Test
    public void givenQuestionPostDto_whenAdd_ThenReturnQuestionGetDtoObject(){

    }

    @DisplayName("Edit question")
    @Test
    public void givenQuestionPostDto_whenUpdate_ThenReturnQuestionGetDtoObject(){

    }

    @DisplayName("Delete question")
    @Test
    public void givenQuestionId_whenDelete_ThenNothing(){

    }

    @DisplayName("Vote up question")
    @Test
    public void givenQuestionId_whenUpVote_ThenReturnUpVotedQuestionGetDtoObject(){

    }

    @DisplayName("Vote down")
    @Test
    public void givenQuestionId_whenDownVote_ThenReturnDownVotedQuestionGetDtoObject(){

    }

    @DisplayName("Find all with valid params")
    @Test
    public void givenValidArgs_whenFindAll_ThenReturnPageObject(){

    }

    @DisplayName("FInd all with too big page number")
    @Test
    public void givenTooBigPageNumber_whenFindAll_thenThrowPageNotFoundException(){

    }

    @DisplayName("Find all with invalid sorting params")
    @Test
    public void givenInvalidSortingArgs_whenFindAll_thenReturnPageObjectWithDefaultSortingArgs(){

    }
}
