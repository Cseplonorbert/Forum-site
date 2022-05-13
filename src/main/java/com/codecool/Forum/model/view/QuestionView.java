package com.codecool.Forum.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class QuestionView {
    @JsonIgnore
    private Long id;
    private String title;
    private String description;
    private Integer view_count;
    private Integer score;
    private String creation_date;
}
