package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionPostDto {

    @NotNull
    @JsonProperty("title")
    private String title;

    @NotNull
    @JsonProperty("message")
    private String message;
}
