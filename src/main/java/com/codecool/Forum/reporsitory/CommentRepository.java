package com.codecool.Forum.reporsitory;

import com.codecool.Forum.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
}
