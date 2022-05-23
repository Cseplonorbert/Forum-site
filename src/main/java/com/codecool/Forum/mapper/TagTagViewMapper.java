package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Tag;
import com.codecool.Forum.model.view.TagView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagTagViewMapper {
    TagView tagTagView(Tag tag);
}
