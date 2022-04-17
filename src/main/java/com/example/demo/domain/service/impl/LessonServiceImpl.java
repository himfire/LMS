package com.example.demo.domain.service.impl;

import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.entity.LessonModule;
import com.example.demo.domain.repository.LessonRepository;
import com.example.demo.domain.service.LessonModuleService;
import com.example.demo.domain.service.LessonService;
import com.example.demo.domain.value.dto.LessonDTO;
import com.example.demo.domain.value.dto.create.CreateLessonDTO;
import com.example.demo.domain.value.dto.update.UpdateLessonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private final ModelMapper modelMapper;
    private final LessonRepository lessonRepository;
    private final LessonModuleService lessonModuleService;

    public LessonServiceImpl(ModelMapper modelMapper, LessonRepository lessonRepository, LessonModuleService lessonModuleService) {
        this.modelMapper = modelMapper;
        this.lessonRepository = lessonRepository;
        this.lessonModuleService = lessonModuleService;
    }

    public List<LessonDTO> getAllLessons(Long lessonModuleId ){
        LessonModule lessonModule = lessonModuleService.getLessonModule(lessonModuleId);
        return lessonRepository.findByLessonsModule(lessonModule)
                .stream()
                .map(lesson -> modelMapper.map(lesson,LessonDTO.class))
                .collect(Collectors.toList());
    }

    public LessonDTO getLessonById(Long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Lesson not found by Id: "+id)
        );
        return modelMapper.map(lesson, LessonDTO.class);
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Lesson not found by: "+id)
                );
    }

    public LessonDTO createLesson(CreateLessonDTO dto){
        Lesson lesson = Lesson.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .lessonsModule(lessonModuleService.getLessonModule(dto.getLessonModuleId()))
                .build();
        return modelMapper.map(lessonRepository.save(lesson),LessonDTO.class);
    }

    public void deleteLesson(Long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Lesson not found"));
        lessonRepository.delete(lesson);
    }

    public LessonDTO updateLesson(UpdateLessonDTO dto, Long id){
        Lesson lessonToUpdate = lessonRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Lesson not found"));
        lessonToUpdate.setContent(dto.getContent());
        lessonToUpdate.setTitle(dto.getTitle());
        lessonToUpdate.setDuration(dto.getDuration());
        //TODO: isValid(lessonToUpdate)
        return modelMapper.map(lessonRepository.save(lessonToUpdate),LessonDTO.class);
    }

    @Override
    public LessonDTO getLessonByTitle(String title) {
        return null;
    }
}
