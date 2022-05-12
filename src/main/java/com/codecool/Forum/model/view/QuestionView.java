package com.codecool.Forum.model.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class QuestionView {
    private Long id;
    private String title;
    private String description;
    private Integer view_count;
    private Integer score;
    private String creation_date;
}
