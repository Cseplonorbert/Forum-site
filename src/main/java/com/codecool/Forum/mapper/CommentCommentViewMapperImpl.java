package com.codecool.Forum.mapper;

import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.view.CommentView;
import org.springframework.stereotype.Component;

@Component
public class CommentCommentViewMapperImpl implements CommentCommentViewMapper {

    @Override
    public CommentView commentCommentView(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentView.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .editedCount(comment.getEditedCount())
                .edited(comment.getEditedCount() > 0)
                .submissionTime(comment.getSubmissionTime())
                .build();
    }
}
