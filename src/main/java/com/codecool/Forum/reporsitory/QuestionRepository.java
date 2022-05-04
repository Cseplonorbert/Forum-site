package com.codecool.Forum.reporsitory;

import com.codecool.Forum.model.Question;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAll(Sort sort);
    Optional<Question> findQuestionById(Long id);
}
