package com.example.demo.domain.service;

import com.example.demo.domain.entity.Course;
import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.value.dto.CourseDTO;
import com.example.demo.domain.value.dto.LessonDTO;
import com.example.demo.domain.value.dto.create.CreateLessonDTO;
import com.example.demo.domain.value.dto.update.UpdateLessonDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface LessonService {

    List<LessonDTO> getAllLessons(Long moduleId);
    LessonDTO getLessonById(Long id);
    Lesson getById(Long id);
    LessonDTO createLesson(CreateLessonDTO dto);
    void deleteLesson(Long id);
    LessonDTO updateLesson(UpdateLessonDTO dto, Long id);
    LessonDTO getLessonByTitle(String title);
}
