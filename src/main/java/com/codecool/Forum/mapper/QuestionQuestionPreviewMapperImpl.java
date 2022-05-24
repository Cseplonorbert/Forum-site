package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.view.QuestionPreview;
import com.codecool.Forum.model.Tag;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QuestionQuestionPreviewMapperImpl
        implements  QuestionQuestionPreviewMapper{

    @Override
    public QuestionPreview questionToQuestionPreview(Question question) {
        if (question == null) {
            return null;
        }
        return QuestionPreview.builder()
                .id(question.getId())
                .viewNumber(question.getViewNumber())
                .voteNumber(question.getVoteNumber())
                .submissionTime(question.getSubmissionTime())
                .tags(question.getTags().stream().map(Tag::getName).collect(Collectors.toList())).build();
    }
}
