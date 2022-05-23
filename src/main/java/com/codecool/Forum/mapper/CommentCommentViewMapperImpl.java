package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.view.CommentView;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class CommentCommentViewMapperImpl implements CommentCommentViewMapper {

    @Override
    public CommentView commentCommentView(Comment comment) {
        return CommentView.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .edited_number_of_times(comment.getEditedNumberOfTimes())
                .edited(comment.isEdited())
                .creation_date(comment.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
