package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.dto.CommentGetDto;
import com.codecool.Forum.model.dto.CommentPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {QuestionDtoMapper.class, AnswerDtoMapper.class}
)
public interface CommentDtoMapper {
    @Mappings({
            @Mapping(target="questionGetDto", source = "question"),
            @Mapping(target="answerGetDto", source = "answer")
    })
    CommentGetDto commentToCommentGetDto(Comment comment);

    @Mappings({
            @Mapping(target="question", source = "questionGetDto"),
            @Mapping(target="answer", source = "answerGetDto")
    })
    Comment commentPostDtoToComment(CommentPostDto commentPostDto);

    @Mappings({
            @Mapping(target="question", source = "questionGetDto"),
            @Mapping(target="answer", source = "answerGetDto")
    })
    Comment commentGetDtoToComment(CommentGetDto commentGetDto);
}
