package com.example.demo.controller;

import com.example.demo.domain.service.LessonService;
import com.example.demo.domain.value.dto.LessonDTO;
import com.example.demo.domain.value.dto.create.CreateLessonDTO;
import com.example.demo.domain.value.dto.update.UpdateLessonDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@CrossOrigin("*")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<LessonDTO> getAllLessonsByLessonModuleId(@RequestParam Long moduleId){
        return lessonService.getAllLessons(moduleId);
    }

    @DeleteMapping("/{id}")
    public void deleteLessonById(@PathVariable Long id){
        lessonService.deleteLesson(id);
    }

    @GetMapping("/{id}")
    public LessonDTO getLessonById(@PathVariable Long id){
        return lessonService.getLessonById(id);
    }

    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(@RequestBody CreateLessonDTO dto){
        return new ResponseEntity<>(lessonService.createLesson(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id,@RequestBody UpdateLessonDTO dto){
        return new ResponseEntity<>(lessonService.updateLesson(dto,id), HttpStatus.CREATED);
    }
}
