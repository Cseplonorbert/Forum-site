package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerGetDto {

    @JsonIgnore
    private Long id;

    @JsonProperty("message")
    private String message;

    @JsonProperty("voteNumber")
    private Integer voteNumber;

    @JsonProperty("submissionTime")
    private LocalDateTime submissionTime;

    @JsonIgnore
    private QuestionGetDto questionGetDto;
}
