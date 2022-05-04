package com.codecool.Forum.reporsitory;

import com.codecool.Forum.model.Question;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAll(Sort sort);
}
