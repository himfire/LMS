package com.example.demo.controller;

import com.example.demo.domain.service.ResourceService;
import com.example.demo.domain.value.dto.LessonDTO;
import com.example.demo.domain.value.dto.ResourceDTO;
import com.example.demo.domain.value.dto.create.CreateLessonDTO;
import com.example.demo.domain.value.dto.create.CreateResourceDTO;
import com.example.demo.domain.value.dto.update.UpdateLessonDTO;
import com.example.demo.domain.value.dto.update.UpdateResourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")
@CrossOrigin("*")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public List<ResourceDTO> getAllResourcesByLessonId(@RequestParam Long lessonId){
        return resourceService.getAllResources(lessonId);
    }

    @DeleteMapping("/{id}")
    public void deleteResourceById(@PathVariable Long id){
        resourceService.deleteResource(id);
    }

    @GetMapping("/{id}")
    public ResourceDTO getResourceById(@PathVariable Long id){
        return resourceService.getResourceById(id);
    }

    @PostMapping("/{lessonId}")
    public ResponseEntity<ResourceDTO> createResource(@RequestBody CreateResourceDTO dto,@PathVariable Long lessonId){
        return new ResponseEntity<>(resourceService.createResource(dto,lessonId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDTO> updateLesson(@PathVariable Long id,@RequestBody UpdateResourceDTO dto){
        return new ResponseEntity<>(resourceService.updateResource(dto,id), HttpStatus.CREATED);
    }
}
