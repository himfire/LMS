package com.example.demo.domain.value.dto;

import com.example.demo.domain.entity.Course;
import com.example.demo.domain.entity.LessonModule;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassificationDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Title must be empty")
    @NotNull(message = "Title must be null")
    @Size(min=6,max = 500,message = "Please add title correctly")
    private String name;

    @Column(unique = true)
    private String code;

    @Column(length = 20)
    @NotBlank(message = "Title must be empty")
    @NotNull(message = "Title must be null")
    @Size(min=6,max = 500,message = "Please add title correctly")
    @ManyToMany(targetEntity = LessonModule.class)
    @JoinTable(name = "classroom_classification",
            joinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "classification_id", referencedColumnName = "id")
    )
    private Set<Course> classifications = new HashSet<>();
}
