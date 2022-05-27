package com.codecool.Forum.service;

import com.codecool.Forum.exception.TagAlreadyAddedToQuestionException;
import com.codecool.Forum.exception.TagNotBeenAddedToQuestionException;
import com.codecool.Forum.exception.TagNotFoundException;
import com.codecool.Forum.mapper.QuestionDtoMapper;
import com.codecool.Forum.mapper.TagDtoMapper;
import com.codecool.Forum.model.Tag;
import com.codecool.Forum.model.dto.QuestionGetDto;
import com.codecool.Forum.model.dto.TagGetDto;
import com.codecool.Forum.model.dto.TagPostDto;
import com.codecool.Forum.reporsitory.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final QuestionService questionService;
    private final TagDtoMapper tagDtoMapper;
    private final QuestionDtoMapper questionDtoMapper;

    @Autowired
    public TagService(TagRepository tagRepository,
                      QuestionService questionService,
                      TagDtoMapper tagDtoMapper,
                      QuestionDtoMapper questionDtoMapper) {
        this.tagRepository = tagRepository;
        this.questionService = questionService;
        this.tagDtoMapper = tagDtoMapper;
        this.questionDtoMapper = questionDtoMapper;
    }

    public Optional<TagGetDto> getTagByName(String tagName) {
        TagGetDto tagGetDto = tagDtoMapper.tagToTagGetDto(tagRepository.findByName(tagName));
        if (tagGetDto == null) {
            return Optional.empty();
        }
        return Optional.of(tagGetDto);
    }

    public List<TagGetDto> getTagsByQuestionId(Long questionId) {
        QuestionGetDto questionGetDto = questionService.getQuestionById(questionId);
        return tagRepository.getTagsByQuestionsContaining(
                questionDtoMapper.questionGetDtoToQuestion(questionGetDto))
                .stream().map(tagDtoMapper::tagToTagGetDto).collect(Collectors.toList());
    }

    public TagGetDto getTagById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            return tagDtoMapper.tagToTagGetDto(tag.get());
        }
        throw new TagNotFoundException(id);
    }

    public TagGetDto add(Long questionId, TagPostDto tagPostDto) {
        Optional<TagGetDto> tagGetDto = getTagByName(tagPostDto.getName());
        QuestionGetDto questionGetDto = questionService.getQuestionById(questionId);
        if (tagGetDto.isPresent()) {
            List<QuestionGetDto> questions = tagGetDto.get().getQuestions();
            if (questions.contains(questionGetDto)) {
                throw new TagAlreadyAddedToQuestionException(tagGetDto.get().getId(), questionGetDto.getId());
            }
            questions.add(questionGetDto);
            tagGetDto.get().setQuestions(questions);
            return tagDtoMapper.tagToTagGetDto(tagRepository.save(tagDtoMapper.tagGetDtoToTag(tagGetDto.get())));
        }
        List<QuestionGetDto> questions = new ArrayList<>();
        questions.add(questionGetDto);
        tagPostDto.setQuestions(questions);
        return tagDtoMapper.tagToTagGetDto(tagRepository.save(tagDtoMapper.tagPostDtoToTag(tagPostDto)));
    }

    public void removeTagFromQuestion(Long questionId, Long tagId) {
        TagGetDto tagGetDto = getTagById(tagId);
        QuestionGetDto questionGetDto = questionService.getQuestionById(questionId);
        if (!tagGetDto.getQuestions().contains(questionGetDto)) {
            throw new TagNotBeenAddedToQuestionException(tagId, questionId);
        }
        tagGetDto.getQuestions().remove(questionGetDto);
        tagRepository.save(tagDtoMapper.tagGetDtoToTag(tagGetDto));
    }
}
