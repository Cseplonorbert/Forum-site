package com.codecool.Forum.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<Question> questions;

    @ManyToMany
    @JoinTable(name = "tag_question",
            joinColumns = @JoinColumn(name = "tag_id",
                    referencedColumnName = "question_id"))
    private List<Question> question = new ArrayList<>();

}
