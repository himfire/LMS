package com.example.demo.domain.entity;

import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Language;
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
    private Long id;

    private String title;

    @Column(unique = true)
    private String slugTitle; //slug titl

    @Lob
    private String description;

    private float price;

    private String courseImageURL;
    private String courseVideoURL;
    private String videoImage;
    @ElementCollection
    private List<String> targetAudience;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(targetEntity = LessonModule.class)
    private Set<LessonModule> lessonModules;

    private Level skillLevel;

    private Long duration;

    private CourseCategory courseCategory;

    private boolean isActive;


    private Language language;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Tag> tags;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Rating rating;

    @OneToMany
    private List<Comment> comments;
    private String contributors;
}
