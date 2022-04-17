package com.example.demo.controller;

import com.example.demo.domain.service.LessonModuleService;
import com.example.demo.domain.value.dto.LessonModuleDTO;
import com.example.demo.domain.value.dto.create.CreateLessonModuleDTO;
import com.example.demo.domain.value.dto.update.UpdateLessonModuleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons-modules")
@CrossOrigin("*")
public class LessonModuleController {

    private final LessonModuleService lessonModuleService;

    public LessonModuleController(LessonModuleService lessonModuleService) {
        this.lessonModuleService = lessonModuleService;
    }

    @GetMapping("")
    public List<LessonModuleDTO> getAllLessonsModuleByCourseId(@RequestParam Long courseId){
        return lessonModuleService.getAllLessonsModuleByCourseId(courseId);
    }

    @GetMapping("/{id}")
    public LessonModuleDTO getLessonsModuleById(@PathVariable(name = "id") Long lessonModuleId){
        return lessonModuleService.getLessonModuleById(lessonModuleId);
    }

    @DeleteMapping("/{id}")
    public void deleteLessonsModule(@PathVariable(name = "id") Long lessonModuleId){
        lessonModuleService.deleteLessonModule(lessonModuleId);
    }

    @PutMapping("/{id}")
    public LessonModuleDTO updateLessonsModule(@PathVariable(name = "id") Long lessonModuleId, @RequestBody UpdateLessonModuleDTO dto){
        return lessonModuleService.updateLessonModule(dto, lessonModuleId);
    }

    @PostMapping()
    public ResponseEntity<LessonModuleDTO> createLessonsModule(@RequestBody CreateLessonModuleDTO dto){
        return new ResponseEntity<>(lessonModuleService.createLessonModule(dto), HttpStatus.CREATED);
    }

}
