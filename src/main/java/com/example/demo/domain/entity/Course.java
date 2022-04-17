package com.example.demo.domain.entity;

import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Level;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="courses")
public class Course extends Auditable{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name="id")
    private Long id;

    @Column(length = 500)
    private String title;

    @Lob
    private String description;

    @Column(name="price")
    private float price;

    @Column(name="course_image")
    private String courseImageURL;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(targetEntity = LessonModule.class)
    private List<LessonModule> lessonModules;

    @Column
    private Level skillLevel;

    @Column
    private CourseCategory courseCategory;

    @Column
    private boolean isActive;

    @OneToOne
    private Language language;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Tag> tags;
}
