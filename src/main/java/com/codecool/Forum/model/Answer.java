package com.codecool.Forum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default private List<Comment> comments = new ArrayList<>();

    @Column(name = "vote_number")
    @Builder.Default private int voteNumber = 0;

    @Column(name = "submission_time")
    @Builder.Default private LocalDateTime submissionTime = LocalDateTime.now();
}
