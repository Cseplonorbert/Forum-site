package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.view.AnswerView;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AnswerAnswerViewImpl implements AnswerAnswerViewMapper{
    @Override
    public AnswerView answerAnswerView(Answer answer) {
        return AnswerView.builder()
                .message(answer.getMessage())
                .score(answer.getNumberOfVotes())
                .creation_date(answer.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
