package com.example.demo.domain.value.dto.create;

import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.entity.Language;
import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Level;
import lombok.Data;

import java.util.Set;

@Data
public class CreateCourseDTO {

    private String title;

    private Level skillLevel;

    private String courseImageURL;

    private float price;
    private String description;
    private Long authorId;

    private Set<Tag> tags;

    private CourseCategory courseCategory;

    private boolean isActive;

    private Language language;
}
