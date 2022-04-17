package com.example.demo.domain.value.dto.update;

import com.example.demo.domain.entity.*;
import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Level;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UpdateCourseDTO {
    private String title;

    private float price;

    private String courseImageURL;

    private Level skillLevel;
    private String description;
    private CourseCategory courseCategory;

    private boolean isActive;

    private Language language;

    private Set<Tag> tags;
}
