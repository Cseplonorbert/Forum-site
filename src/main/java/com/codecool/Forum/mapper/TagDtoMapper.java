package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Tag;
import com.codecool.Forum.model.dto.TagGetDto;
import com.codecool.Forum.model.dto.TagPostDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {QuestionDtoMapper.class}
)
public interface TagDtoMapper {
    TagGetDto tagToTagGetDto(Tag tag);
    Tag tagPostDtoToTag(TagPostDto tagPostDto);
    Tag tagGetDtoToTag(TagGetDto tagGetDto);
}
