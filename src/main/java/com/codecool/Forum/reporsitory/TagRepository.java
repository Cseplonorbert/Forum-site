package com.codecool.Forum.reporsitory;

import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    List<Tag> getTagsByQuestionsContaining(Question question);
}
