package com.codecool.Forum.reporsitory;

import com.codecool.Forum.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer save(Answer answer);
    List<Answer> findAnswersByQuestionId(Long id);
}
