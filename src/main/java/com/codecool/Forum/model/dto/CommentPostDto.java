package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CommentPostDto {

    @NotNull
    @JsonProperty("message")
    private String message;

    private QuestionGetDto questionGetDto;

    private AnswerGetDto answerGetDto;
}
