package com.example.demo.controller;

import com.example.demo.domain.service.impl.CourseServiceImpl;
import com.example.demo.domain.value.dto.CourseDTO;
import com.example.demo.domain.value.dto.create.CreateCourseDTO;
import com.example.demo.domain.value.dto.update.UpdateCourseDTO;
import com.example.demo.domain.value.dto.update.UpdateCourseTagsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/courses")
@CrossOrigin("*")
public class CourseController {

    Logger _log = LoggerFactory.getLogger(UserController.class);
    private final CourseServiceImpl courseService;

    public CourseController(CourseServiceImpl classroomService) {
        this.courseService = classroomService;
    }

    @GetMapping("/search")
    public Page<CourseDTO> getAllCourses(@RequestParam Map<String, Object> queryParams){
        return  courseService.getAllCourses(queryParams);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity getCouresById(@PathVariable Long id){
//        return new ResponseEntity(courseService.getCourseById(id),HttpStatus.OK);
//    }

    @GetMapping("/{title}")
    public CourseDTO getCoursesByTitle(@PathVariable String title){
        return courseService.getCourseBySlugTitle(title);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CreateCourseDTO dto){
        return new ResponseEntity<>(courseService.createCourse(dto),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@RequestBody UpdateCourseDTO dto, @PathVariable Long id){
        return new ResponseEntity(courseService.updateCourse(dto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }

    @PutMapping("/{id}/tags")
    public ResponseEntity updateCourseTags(@RequestBody UpdateCourseTagsDTO dto, @PathVariable Long id){
        return new ResponseEntity(courseService.updateCourseTags(id,dto),HttpStatus.OK);
    }
}
