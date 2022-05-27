package com.codecool.Forum.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.codecool.Forum.controller.AnswerController;
import com.codecool.Forum.controller.CommentController;
import com.codecool.Forum.controller.QuestionController;
import com.codecool.Forum.controller.TagController;
import com.codecool.Forum.model.dto.QuestionGetDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class QuestionAssembler implements RepresentationModelAssembler<QuestionGetDto, EntityModel<QuestionGetDto>> {

    @Override
    public EntityModel<QuestionGetDto> toModel(QuestionGetDto question) {

        return EntityModel.of(question,
                linkTo(methodOn(QuestionController.class).get(question.getId())).withSelfRel(),
                linkTo(methodOn(AnswerController.class)
                        .getAllAnswersByQuestionId(question.getId())).withRel("answers"),
                linkTo(methodOn(CommentController.class).getAllByQuestionId(question.getId())).withRel("comments"),
                linkTo(methodOn(TagController.class).getAllByQuestionId(question.getId())).withRel("tags"));
    }
}
