package com.codecool.Forum.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class QuestionGetDto {

    @JsonIgnore
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;

    @JsonProperty("viewNumber")
    private int viewNumber;

    @JsonProperty("voteNumber")
    private int voteNumber;

    @JsonProperty("submissionTime")
    private LocalDateTime submissionTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof QuestionGetDto))
            return false;

        QuestionGetDto other = (QuestionGetDto) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
