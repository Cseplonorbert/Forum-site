package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AnswerPostDto {

    @NotNull
    @JsonProperty("message")
    @Size(min = 30, message = "Answer message is too short")
    @Size(max = 30000, message = "Answer message is too long")
    private String message;

    private QuestionGetDto questionGetDto;
}
