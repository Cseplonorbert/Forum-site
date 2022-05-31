package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionPostDto {

    @NotNull
    @Size(min = 20, message = "Question title is too short")
    @Size(max = 600, message = "Question title is too long")
    @JsonProperty("title")
    private String title;

    @NotNull
    @Size(min = 30, message = "Question message is too short")
    @Size(max = 30000, message = "Question message is too long")
    @JsonProperty("message")
    private String message;
}
