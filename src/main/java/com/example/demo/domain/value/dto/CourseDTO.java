package com.example.demo.domain.value.dto;


import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Language;
import com.example.demo.domain.value.enumurator.Level;
import lombok.*;

import javax.persistence.Lob;
import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {

    private Long id;
    private String title;
    private String slugTitle;
    private String description;
    private Level skillLevel;
    private float price;
    private Long duration;
    private String courseImageURL;
    private String courseVideoURL;
    private boolean isFeatured;
    private UserDTO author;
    private Language language;
    private Set<LessonModuleDTO> lessonModules;
    private Set<TagDTO> tags;
    private String contributors;
    private boolean isActive;
}
