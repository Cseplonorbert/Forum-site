package com.codecool.Forum.model.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnswerView {

    private String message;

    private Integer score;

    private String creation_date;

}
