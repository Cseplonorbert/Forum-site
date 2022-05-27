package com.codecool.Forum.model;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "questions")
    List<Tag> tags = new ArrayList<>();

    @Column(name = "view_number")
    private int viewNumber = 0;

    @Column(name = "vote_number")
    private int voteNumber = 0;

    @Column(name = "submission_time")
    private LocalDateTime submissionTime = LocalDateTime.now();
}
