package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.QuestionPreview;
import com.codecool.Forum.model.Tag;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class QuestionQuestionPreviewMapperImpl
        implements  QuestionQuestionPreviewMapper{

    @Override
    public QuestionPreview questionToQuestionPreview(Question question) {
        if (question == null) {
            return null;
        }
        QuestionPreview questionPreview = new QuestionPreview();
        questionPreview.setId(question.getId());
        questionPreview.setTitle(question.getTitle());
        questionPreview.setView_count(question.getViewed());
        questionPreview.setScore(question.getNumberOfVotes());
        questionPreview.setCreation_date(question.getCreatedOn().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        questionPreview.setTags(question.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        return questionPreview;
    }
}
