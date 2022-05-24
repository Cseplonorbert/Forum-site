package com.codecool.Forum.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
public class QuestionPreview {

    @JsonIgnore
    private Long id;

    private String title;
    private Integer viewNumber;
    private Integer voteNumber;
    private LocalDateTime submissionTime;
    private List<String> tags;
}
