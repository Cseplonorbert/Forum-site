package com.codecool.Forum.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.codecool.Forum.controller.AnswerController;
import com.codecool.Forum.mapper.AnswerAnswerViewMapper;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.view.AnswerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AnswerViewAssembler implements RepresentationModelAssembler<Answer,
        EntityModel<AnswerView>> {

    private final AnswerAnswerViewMapper answerAnswerViewMapper;

    @Autowired
    public AnswerViewAssembler(AnswerAnswerViewMapper answerAnswerViewMapper) {
        this.answerAnswerViewMapper = answerAnswerViewMapper;
    }

    @Override
    public EntityModel<AnswerView> toModel(Answer answer) {
        AnswerView answerView = answerAnswerViewMapper.answerAnswerView(answer);
        return EntityModel.of(answerView,
                linkTo(methodOn(AnswerController.class).getAnswerById(answer.getId())).withSelfRel(),
                linkTo(methodOn(AnswerController.class).upVote(answer.getId())).withRel("upVote"),
                linkTo(methodOn(AnswerController.class).downVote(answer.getId())).withRel("downVote"));
    }

    @Override
    public CollectionModel<EntityModel<AnswerView>> toCollectionModel(Iterable<? extends Answer> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
