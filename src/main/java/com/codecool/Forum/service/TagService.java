package com.codecool.Forum.service;

import com.codecool.Forum.exception.TagAlreadyAddedToQuestionException;
import com.codecool.Forum.exception.TagNotBeenAddedToQuestionException;
import com.codecool.Forum.exception.TagNotFoundException;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Tag;
import com.codecool.Forum.reporsitory.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> getTagByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    public List<Tag> getTagsByQuestion(Question question) {
        return tagRepository.getTagsByQuestionsContaining(question);
    }

    public Tag getTagById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            return tag.get();
        }
        throw new TagNotFoundException(id);
    }

    public void add(String tagName, Question question) {
        Optional<Tag> tag = getTagByName(tagName.toLowerCase());
        if (!tag.isPresent()) {
            tag = Optional.of(Tag.builder().name(tagName.toLowerCase()).build());
        }
        List<Question> questions = tag.get().getQuestions();
        if (question.getTags().contains(tag.get())) {
            throw new TagAlreadyAddedToQuestionException(question.getId(), tag.get().getId());
        }
        questions.add(question);
        tag.get().setQuestions(questions);
        tagRepository.save(tag.get());
    }

    public void removeTagFromQuestion(Tag tag, Question question) {
        List<Question> questions = tag.getQuestions();
        boolean removed = questions.remove(question);
        if (!removed) {
            throw new TagNotBeenAddedToQuestionException(question.getId(), tag.getId());
        }
        tag.setQuestions(questions);
        tagRepository.save(tag);
    }
}
