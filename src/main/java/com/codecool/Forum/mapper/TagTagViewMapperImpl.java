package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Tag;
import com.codecool.Forum.model.view.TagView;
import org.springframework.stereotype.Component;

@Component
public class TagTagViewMapperImpl implements TagTagViewMapper{
    @Override
    public TagView tagTagView(Tag tag) {
        if (tag == null) {
            return null;
        }
        return TagView.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
