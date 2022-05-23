package com.codecool.Forum.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CommentView {
    @JsonIgnore
    private Long id;
    private String message;
    private int edited_number_of_times;
    private boolean edited;
    private String creation_date;
}
