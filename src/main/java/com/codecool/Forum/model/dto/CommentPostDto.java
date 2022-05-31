package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class CommentPostDto {

    @NotNull
    @JsonProperty("message")
    @Size(min = 30, message = "Comment message is too short")
    @Size(max = 30000, message = "Comment message is too long")
    private String message;

    private QuestionGetDto questionGetDto;

    private AnswerGetDto answerGetDto;
}
