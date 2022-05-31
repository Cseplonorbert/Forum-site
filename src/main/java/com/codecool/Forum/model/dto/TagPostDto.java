package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class TagPostDto {

    @NotNull
    @JsonProperty("name")
    @Size(min = 2, message = "Tag name is too short")
    @Size(max = 15, message = "Tag name is too long")
    private String name;

    @JsonIgnore
    private List<QuestionGetDto> questions;
}
