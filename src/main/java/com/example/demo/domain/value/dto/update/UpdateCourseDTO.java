package com.example.demo.domain.value.dto.update;

import com.example.demo.domain.entity.*;
import com.example.demo.domain.value.dto.LessonModuleDTO;
import com.example.demo.domain.value.dto.TagDTO;
import com.example.demo.domain.value.dto.UserDTO;
import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Language;
import com.example.demo.domain.value.enumurator.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseDTO {
    private String title;
    private String slugTitle;
    private Level skillLevel;
    private String description;
    private float price;
    private Long duration;
    private String courseImageURL;
    private String courseVideoURL;
    private UserDTO author;
    private Language language;
    private Set<LessonModuleDTO> lessonModules;
    private Set<TagDTO> tags;
    private CourseCategory courseCategory;
    private String contributors;
    private boolean isActive;
}
