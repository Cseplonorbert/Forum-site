package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagGetDto {

    @JsonIgnore
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonIgnore
    private List<QuestionGetDto> questions;
}
