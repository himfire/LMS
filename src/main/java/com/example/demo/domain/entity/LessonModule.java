package com.example.demo.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Lessons_Modules")
public class LessonModule extends Auditable{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(length = 300)
    private String title;

    @Column(length = 800)
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Lesson> lessons;

    @ManyToOne
    private Course course;
}
