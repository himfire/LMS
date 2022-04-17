package com.example.demo.domain.service.impl;

import com.example.demo.domain.entity.LessonModule;
import com.example.demo.domain.repository.LessonsModuleRepository;
import com.example.demo.domain.service.CourseService;
import com.example.demo.domain.service.LessonModuleService;
import com.example.demo.domain.value.dto.LessonModuleDTO;
import com.example.demo.domain.value.dto.create.CreateLessonModuleDTO;
import com.example.demo.domain.value.dto.update.UpdateLessonModuleDTO;
import com.example.demo.utility.LessonsModuleConvertor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LessonModuleServiceImpl implements LessonModuleService {

    private final ModelMapper modelMapper;
    private final LessonsModuleRepository lessonsModuleRepository;
    private final CourseService courseService;

    public LessonModuleServiceImpl(ModelMapper modelMapper, LessonsModuleRepository lessonsModuleRepository, CourseService courseService) {
        this.modelMapper = modelMapper;
        this.lessonsModuleRepository = lessonsModuleRepository;
        this.courseService = courseService;
    }

    public Page<LessonModuleDTO> getAllLessonsModule(Pageable pageable){
        return new PageImpl<>(LessonsModuleConvertor.convertListOfEntityToDTO(
                lessonsModuleRepository.findAll(pageable).getContent()
        ), lessonsModuleRepository.findAll(pageable).getPageable(),
                lessonsModuleRepository.findAll(pageable).getTotalElements()
        );
    }

    public LessonModuleDTO getLessonModuleById(long id) {
        LessonModule lessonModule = lessonsModuleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found by Id: " + id)
        );
        return modelMapper.map(lessonModule, LessonModuleDTO.class);
    }

    @Override
    public List<LessonModuleDTO> getAllLessonsModuleByCourseId(Long courseId) {
        return null;
    }

    @Override
    public LessonModuleDTO getLessonModuleById(Long id) {
        LessonModule lessonModule = lessonsModuleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found by Id: "+id)
        );
        return modelMapper.map(lessonModule, LessonModuleDTO.class);
    }

    @Override
    public LessonModule getLessonModule(Long id) {
        return lessonsModuleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lesson module not found"));
    }

    @Override
    public LessonModuleDTO createLessonModule(CreateLessonModuleDTO dto) {
        LessonModule lessonModule = LessonModule.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .course(courseService.getById(dto.getCourseId()))
                .build();
        //TODO: isValide(lessonModule)
        final LessonModule save = lessonsModuleRepository.save(lessonModule);
        return modelMapper.map(save,LessonModuleDTO.class);
    }

    @Override
    public void deleteLessonModule(Long id) {
        LessonModule lessonModule = lessonsModuleRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Lesson Module not found"));
        lessonsModuleRepository.delete(lessonModule);
    }

    @Override
    public LessonModuleDTO updateLessonModule(UpdateLessonModuleDTO dto, Long id) {
        LessonModule lessonModuleToUpdate = LessonModule.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        //TODO: isValid(lessonModuleToUpdate)
        return modelMapper.map(lessonsModuleRepository.save(lessonModuleToUpdate),LessonModuleDTO.class);
    }
}
