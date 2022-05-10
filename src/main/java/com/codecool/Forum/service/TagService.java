package com.codecool.Forum.service;

import com.codecool.Forum.exception.TagAlreadyAddedToQuestionException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Tag;
import com.codecool.Forum.reporsitory.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> getTagByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    public void add(String tagName, Question question) {
        Optional<Tag> tag = getTagByName(tagName.toLowerCase());
        if (!tag.isPresent()) {
            tag = Optional.of(Tag.builder().name(tagName.toLowerCase()).build());
        }
        List<Question> questions = tag.get().getQuestions();
        if (question.getTags().contains(tag.get())) {
            throw new TagAlreadyAddedToQuestionException("Tag already added to the question");
        }
        questions.add(question);
        tag.get().setQuestions(questions);
        tagRepository.save(tag.get());
    }
}
