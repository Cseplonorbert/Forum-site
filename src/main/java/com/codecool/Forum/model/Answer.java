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
@Table(name = "ANSWERS")
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

    @Builder.Default private int numberOfVotes = 0;

    @Builder.Default private LocalDateTime createdOn = LocalDateTime.now();
}
