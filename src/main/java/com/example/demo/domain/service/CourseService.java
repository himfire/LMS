package com.example.demo.domain.service;

import com.example.demo.domain.entity.Course;
import com.example.demo.domain.value.dto.CourseDTO;
import com.example.demo.domain.value.dto.create.CreateCourseDTO;
import com.example.demo.domain.value.dto.update.UpdateCourseDTO;
import com.example.demo.domain.value.dto.update.UpdateCourseTagsDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CourseService {
    Page<CourseDTO> getAllCourses(Map<String,Object> filterOption);
    CourseDTO getCourseById(Long id);
    Course getById(Long id);
    CourseDTO createCourse(CreateCourseDTO dto);
    void deleteCourse(Long id);
    CourseDTO updateCourse(UpdateCourseDTO dto, Long id);
    CourseDTO updateCourseTags(Long courseId, UpdateCourseTagsDTO dto);
}
