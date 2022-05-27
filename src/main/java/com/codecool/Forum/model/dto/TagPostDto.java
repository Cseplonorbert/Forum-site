package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TagPostDto {

    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonIgnore
    private List<QuestionGetDto> questions;
}
