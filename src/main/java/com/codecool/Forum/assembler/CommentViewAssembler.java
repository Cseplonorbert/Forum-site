package com.codecool.Forum.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.codecool.Forum.controller.CommentController;
import com.codecool.Forum.mapper.CommentCommentViewMapper;
import com.codecool.Forum.model.Comment;
import com.codecool.Forum.model.view.CommentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CommentViewAssembler implements RepresentationModelAssembler<Comment, EntityModel<CommentView>> {

    private final CommentCommentViewMapper commentCommentViewMapper;

    @Autowired
    public CommentViewAssembler(CommentCommentViewMapper commentCommentViewMapper) {
        this.commentCommentViewMapper = commentCommentViewMapper;
    }

    @Override
    public EntityModel<CommentView> toModel(Comment comment) {
        CommentView commentView = commentCommentViewMapper.commentCommentView(comment);
        return EntityModel.of(commentView,
                linkTo(methodOn(CommentController.class).get(comment.getId())).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<CommentView>> toCollectionModel(Iterable<? extends Comment> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
