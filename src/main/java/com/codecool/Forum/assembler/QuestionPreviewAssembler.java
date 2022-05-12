package com.codecool.Forum.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.codecool.Forum.controller.QuestionController;
import com.codecool.Forum.mapper.QuestionQuestionPreviewMapper;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.QuestionPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class QuestionPreviewAssembler implements RepresentationModelAssembler<Question,
        EntityModel<QuestionPreview>> {

    QuestionQuestionPreviewMapper questionQuestionPreviewMapper;

    @Autowired
    public QuestionPreviewAssembler(QuestionQuestionPreviewMapper questionQuestionPreviewMapper) {
        this.questionQuestionPreviewMapper = questionQuestionPreviewMapper;
    }

    @Override
    public EntityModel<QuestionPreview> toModel(Question question) {
        QuestionPreview questionPreview = questionQuestionPreviewMapper.questionToQuestionPreview(question);

        return EntityModel.of(questionPreview,
                linkTo(methodOn(QuestionController.class).getQuestionById(questionPreview.getId())).withSelfRel());
    }
}
