package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.view.AnswerView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerAnswerViewMapper {
    AnswerView answerAnswerView(Answer answer);
}
