package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.QuestionPreview;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionQuestionPreviewMapper {
    QuestionPreview questionToQuestionPreview(Question question);
}
