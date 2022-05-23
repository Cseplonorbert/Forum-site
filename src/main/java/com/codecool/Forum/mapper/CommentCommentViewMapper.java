package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.view.CommentView;
import org.mapstruct.Mapper;

@Mapper
public interface CommentCommentViewMapper {
    CommentView commentCommentView(Comment comment);
}
