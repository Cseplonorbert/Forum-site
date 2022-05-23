package com.codecool.Forum.assembler;

import com.codecool.Forum.mapper.TagTagViewMapper;
import com.codecool.Forum.model.Tag;
import com.codecool.Forum.model.view.TagView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TagViewAssembler implements RepresentationModelAssembler<Tag,
        EntityModel<TagView>> {

    private final TagTagViewMapper tagTagViewMapper;

    @Autowired
    public TagViewAssembler(TagTagViewMapper tagTagViewMapper) {
        this.tagTagViewMapper = tagTagViewMapper;
    }

    @Override
    public EntityModel<TagView> toModel(Tag tag) {
        TagView tagView = tagTagViewMapper.tagTagView(tag);
        return EntityModel.of(tagView);
    }

    @Override
    public CollectionModel<EntityModel<TagView>> toCollectionModel(Iterable<? extends Tag> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
