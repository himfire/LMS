package com.example.demo.domain.value.dto.create;

import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.entity.User;
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

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDTO {
    private String title;
    private String slugTitle;
    private String description;
    private Level skillLevel;
    private float price;
    private Long duration;
    private String courseImageURL;
    private String courseVideoURL;
    private Long author;
    private Language language;
    private Set<TagDTO> tags;
    private String contributors;
}
