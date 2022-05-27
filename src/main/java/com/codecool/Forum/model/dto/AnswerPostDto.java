package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AnswerPostDto {

    @NotNull
    @JsonProperty("message")
    private String message;

    private QuestionGetDto questionGetDto;
}
