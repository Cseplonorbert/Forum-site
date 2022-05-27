package com.codecool.Forum.controller;

import com.codecool.Forum.assembler.TagAssembler;
import com.codecool.Forum.model.dto.TagGetDto;
import com.codecool.Forum.model.dto.TagPostDto;
import com.codecool.Forum.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TagController {

    private final TagService tagService;
    private final TagAssembler tagViewAssembler;

    public TagController(TagService tagService, TagAssembler tagViewAssembler) {
        this.tagService = tagService;
        this.tagViewAssembler = tagViewAssembler;
    }

    @GetMapping("/questions/{questionId}/tags")
    public CollectionModel<EntityModel<TagGetDto>> getAllByQuestionId(@PathVariable Long questionId) {
        return tagViewAssembler.toCollectionModel(tagService.getTagsByQuestionId(questionId));
    }

    @PostMapping("/questions/{questionId}/tags")
    public EntityModel<TagGetDto> add(@PathVariable Long questionId, @RequestBody TagPostDto tagPostDto) {
        return tagViewAssembler.toModel(tagService.add(questionId, tagPostDto));
    }

    @DeleteMapping("/questions/{questionId}/tags/{tagId}")
    public ResponseEntity<Void> deleteTagFromQuestion(@PathVariable Long questionId,
                                                      @PathVariable Long tagId) {
        tagService.removeTagFromQuestion(questionId, tagId);
        return ResponseEntity.noContent().build();
    }
}
