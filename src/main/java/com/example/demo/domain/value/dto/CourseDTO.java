package com.example.demo.domain.value.dto;


import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Level;
import lombok.*;

import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {

    private Long id;

    private String title;

    private Level skillLevel;

    private float price;

    private UserDTO author;

    private Set<LessonModuleDTO> lessonModules;

    private Set<TagDTO> tags;

    private CourseCategory courseCategory;

    private boolean isActive;
}
