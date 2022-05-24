package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.TagViewAssembler;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Tag;
import com.codecool.Forum.model.view.TagView;
import com.codecool.Forum.service.QuestionService;
import com.codecool.Forum.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TagController {

    private final QuestionService questionService;
    private final TagService tagService;
    private final TagViewAssembler tagViewAssembler;

    public TagController(QuestionService questionService, TagService tagService, TagViewAssembler tagViewAssembler) {
        this.questionService = questionService;
        this.tagService = tagService;
        this.tagViewAssembler = tagViewAssembler;
    }

    @GetMapping("/questions/{questionId}/tags")
    public CollectionModel<EntityModel<TagView>> getAllByQuestionId(@PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId);
        return tagViewAssembler.toCollectionModel(tagService.getTagsByQuestion(question));
    }

    @PostMapping("/questions/{questionId}/tags")
    public CollectionModel<EntityModel<TagView>> add(@PathVariable Long questionId, @RequestBody String message) {
        Question question = questionService.getQuestionById(questionId);
        tagService.add(message, question);
        return getAllByQuestionId(questionId);
    }

    @DeleteMapping("/questions/{questionId}/tags/{tagId}")
    public CollectionModel<EntityModel<TagView>> deleteTagFromQuestion(@PathVariable Long questionId,
                                                                       @PathVariable Long tagId) {
        Question question = questionService.getQuestionById(questionId);
        Tag tag = tagService.getTagById(tagId);
        tagService.removeTagFromQuestion(tag, question);
        return getAllByQuestionId(questionId);
    }
}
