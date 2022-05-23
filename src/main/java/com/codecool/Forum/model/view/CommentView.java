package com.codecool.Forum.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class CommentView {

    @JsonIgnore
    private Long id;

    private String message;
    private int editedCount;
    private boolean edited;
    private LocalDateTime submissionTime;
}
