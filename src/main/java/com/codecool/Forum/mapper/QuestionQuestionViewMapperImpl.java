package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.view.QuestionView;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class QuestionQuestionViewMapperImpl implements QuestionQuestionViewMapper{
    @Override
    public QuestionView questionQuestionView(Question question) {
        return QuestionView.builder()
                .id(question.getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .view_count(question.getViewed())
                .score(question.getNumberOfVotes())
                .creation_date(question.getCreatedOn().
                        format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
