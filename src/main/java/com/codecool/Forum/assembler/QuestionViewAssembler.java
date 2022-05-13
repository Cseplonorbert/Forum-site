package com.codecool.Forum.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.codecool.Forum.controller.QuestionController;
import com.codecool.Forum.mapper.QuestionQuestionViewMapper;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.view.QuestionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class QuestionViewAssembler implements RepresentationModelAssembler<Question, EntityModel<QuestionView>> {

    private final QuestionQuestionViewMapper questionQuestionViewMapper;

    @Autowired
    public QuestionViewAssembler(QuestionQuestionViewMapper questionQuestionViewMapper) {
        this.questionQuestionViewMapper = questionQuestionViewMapper;
    }


    @Override
    public EntityModel<QuestionView> toModel(Question question) {
        QuestionView questionView = questionQuestionViewMapper.questionQuestionView(question);

        return EntityModel.of(questionView,
                linkTo(methodOn(QuestionController.class).get(questionView.getId())).withSelfRel(),
                linkTo(methodOn(QuestionController.class).getAnswers(questionView.getId())).withRel("answers"),
                linkTo(methodOn(QuestionController.class).getComments(questionView.getId())).withRel("comments"),
                linkTo(methodOn(QuestionController.class).getTags(questionView.getId())).withRel("tags"));
    }
}
