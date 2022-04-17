package com.example.demo.domain.service;

import com.example.demo.domain.entity.LessonModule;
import com.example.demo.domain.value.dto.LessonModuleDTO;
import com.example.demo.domain.value.dto.create.CreateLessonModuleDTO;
import com.example.demo.domain.value.dto.update.UpdateLessonModuleDTO;

import java.util.List;

public interface LessonModuleService {

    List<LessonModuleDTO> getAllLessonsModuleByCourseId(Long courseId);
    LessonModuleDTO getLessonModuleById(Long id);
    LessonModule getLessonModule(Long id);
    LessonModuleDTO createLessonModule(CreateLessonModuleDTO dto);
    void deleteLessonModule(Long id);
    LessonModuleDTO updateLessonModule(UpdateLessonModuleDTO dto, Long id);
}
