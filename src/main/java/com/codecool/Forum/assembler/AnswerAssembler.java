package com.codecool.Forum.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.codecool.Forum.controller.AnswerController;
import com.codecool.Forum.model.dto.AnswerGetDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AnswerAssembler implements RepresentationModelAssembler<AnswerGetDto,
        EntityModel<AnswerGetDto>> {

    @Override
    public EntityModel<AnswerGetDto> toModel(AnswerGetDto answerGetDto) {
        return EntityModel.of(answerGetDto,
                linkTo(methodOn(AnswerController.class).getAnswerById(answerGetDto.getId())).withSelfRel(),
                linkTo(methodOn(AnswerController.class).upVote(answerGetDto.getId())).withRel("upVote"),
                linkTo(methodOn(AnswerController.class).downVote(answerGetDto.getId())).withRel("downVote"));
    }

    @Override
    public CollectionModel<EntityModel<AnswerGetDto>> toCollectionModel(Iterable<? extends AnswerGetDto> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
