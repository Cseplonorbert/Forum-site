package com.codecool.Forum.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AnswerView {

    @JsonIgnore
    private Long id;

    private String message;

    private Integer voteNumber;

    private LocalDateTime submissionTime;

}
