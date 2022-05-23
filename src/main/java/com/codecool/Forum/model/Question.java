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
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Builder.Default List<Tag> tags = new ArrayList<>();

    @Column(name = "view_number")
    @Builder.Default private int viewNumber = 0;

    @Column(name = "vote_number")
    @Builder.Default private int voteNumber = 0;

    @Column(name = "submission_time")
    @Builder.Default private LocalDateTime submissionTime = LocalDateTime.now();

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
