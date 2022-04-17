package com.example.demo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Lessons")
public class Lesson extends Auditable{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(length = 300)
    private String title;

    @Lob
    private String content;

    @Column
    private String duration;

    @OneToMany
    private Set<Resource> resources;

    @ManyToOne
    private LessonModule lessonsModule;
}
