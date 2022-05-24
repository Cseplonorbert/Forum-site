package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.view.AnswerView;
import org.springframework.stereotype.Component;

@Component
public class AnswerAnswerViewImpl implements AnswerAnswerViewMapper{
    @Override
    public AnswerView answerAnswerView(Answer answer) {
        if (answer == null) {
            return null;
        }
        return AnswerView.builder()
                .message(answer.getMessage())
                .voteNumber(answer.getVoteNumber())
                .submissionTime(answer.getSubmissionTime())
                .build();
    }
}
