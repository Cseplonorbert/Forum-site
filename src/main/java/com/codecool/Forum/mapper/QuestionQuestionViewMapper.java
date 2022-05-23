package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.view.QuestionView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionQuestionViewMapper {
    QuestionView questionQuestionView(Question question);
}
