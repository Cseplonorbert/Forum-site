package com.codecool.Forum.reporsitory;

import com.codecool.Forum.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
}
