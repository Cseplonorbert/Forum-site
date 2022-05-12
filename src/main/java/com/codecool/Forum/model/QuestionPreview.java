package com.codecool.Forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionPreview {

    @JsonIgnore
    private Long id;
    private String title;
    private Integer view_count;
    private Integer score;
    private String creation_date;
    private List<String> tags;
}
