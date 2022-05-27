package com.codecool.Forum.assembler;

import com.codecool.Forum.model.dto.TagGetDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TagAssembler implements RepresentationModelAssembler<TagGetDto, EntityModel<TagGetDto>> {

    @Override
    public EntityModel<TagGetDto> toModel(TagGetDto tagGetDto) {
        return EntityModel.of(tagGetDto);
    }

    @Override
    public CollectionModel<EntityModel<TagGetDto>> toCollectionModel(Iterable<? extends TagGetDto> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
