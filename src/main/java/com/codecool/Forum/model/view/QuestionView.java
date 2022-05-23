package com.codecool.Forum.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class QuestionView {

    @JsonIgnore
    private Long id;

    private String title;
    private String message;
    private Integer viewNumber;
    private Integer voteNumber;
    private LocalDateTime submissionTime;
}
