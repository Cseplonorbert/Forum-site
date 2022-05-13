package com.codecool.Forum.model;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "questions")
    @Builder.Default List<Tag> tags = new ArrayList<>();

    @Builder.Default private int viewed = 0;

    @Builder.Default private int numberOfVotes = 0;

    @Builder.Default private LocalDateTime createdOn = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Question))
            return false;

        Question other = (Question) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
