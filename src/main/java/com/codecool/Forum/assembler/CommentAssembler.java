package com.codecool.Forum.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.codecool.Forum.controller.CommentController;
import com.codecool.Forum.model.dto.CommentGetDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CommentAssembler implements RepresentationModelAssembler<CommentGetDto, EntityModel<CommentGetDto>> {

    @Override
    public EntityModel<CommentGetDto> toModel(CommentGetDto commentGetDto) {
        return EntityModel.of(commentGetDto,
                linkTo(methodOn(CommentController.class).get(commentGetDto.getId())).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<CommentGetDto>> toCollectionModel(Iterable<? extends CommentGetDto> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
