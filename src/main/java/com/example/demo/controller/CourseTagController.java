package com.example.demo.controller;

import com.example.demo.domain.service.CourseTagsService;
import com.example.demo.domain.value.dto.TagDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tags")
@CrossOrigin("*")
public class CourseTagController {

    Logger _log = LoggerFactory.getLogger(UserController.class);
    private final CourseTagsService courseTagsService;

    public CourseTagController(CourseTagsService courseTagsService) {
        this.courseTagsService = courseTagsService;
    }

    @GetMapping
    public List<TagDTO> getAllTags(@RequestParam String name){
        return courseTagsService.getAllTagsByName(name);
    }


    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO dto){
        return new ResponseEntity<>(courseTagsService.createTag(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TagDTO updateTag(@PathVariable Long id, @RequestBody TagDTO dto){
        return courseTagsService.updateCourseTag(dto,id);
    }

    @DeleteMapping("/{id}")
    public void updateTag(@PathVariable Long id){
        courseTagsService.deleteTag(id);
    }

}
