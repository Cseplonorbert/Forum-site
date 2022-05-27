package com.codecool.Forum.reporsitory;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
    List<Tag> getTagsByQuestionsContaining(Question question);
}
