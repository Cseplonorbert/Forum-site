package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.dto.QuestionGetDto;
import com.codecool.Forum.model.dto.QuestionPostDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface QuestionDtoMapper {
    QuestionGetDto questionToQuestionGetDto(Question question);
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
    Question questionGetDtoToQuestion(QuestionGetDto questionGetDto);
}
