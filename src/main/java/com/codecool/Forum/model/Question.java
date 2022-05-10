package com.codecool.Forum.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<Comment> comments;

    @ManyToMany(mappedBy = "questions")
    List<Tag> tags = new ArrayList<>();

    @Builder.Default private int viewed = 0;

    @Builder.Default private int numberOfVotes = 0;

    @JsonIgnore
    @Builder.Default private LocalDateTime createdOn = LocalDateTime.now();
    @Builder.Default private String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

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
