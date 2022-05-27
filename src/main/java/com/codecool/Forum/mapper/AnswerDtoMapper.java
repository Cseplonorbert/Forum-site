package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.dto.AnswerGetDto;
import com.codecool.Forum.model.dto.AnswerPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {QuestionDtoMapper.class}
)
public interface AnswerDtoMapper {
    @Mappings({
            @Mapping(target="questionGetDto", source = "question")
    })
    AnswerGetDto answerToAnswerGetDto(Answer answer);

    @Mappings({
            @Mapping(target="question", source = "questionGetDto")
    })
    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);

    @Mappings({
            @Mapping(target="question", source = "questionGetDto")
    })
    Answer answerGetDtoToAnswer(AnswerGetDto answerGetDto);
}
