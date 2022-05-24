package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.view.QuestionView;
import org.springframework.stereotype.Component;

@Component
public class QuestionQuestionViewMapperImpl implements QuestionQuestionViewMapper{
    @Override
    public QuestionView questionQuestionView(Question question) {
        if (question == null) {
            return null;
        }
        return QuestionView.builder()
                .id(question.getId())
                .title(question.getTitle())
                .message(question.getMessage())
                .viewNumber(question.getViewNumber())
                .voteNumber(question.getVoteNumber())
                .submissionTime(question.getSubmissionTime())
                .build();
    }
}
