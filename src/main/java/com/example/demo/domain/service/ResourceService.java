package com.example.demo.domain.service;

import com.example.demo.domain.entity.Course;
import com.example.demo.domain.entity.Resource;
import com.example.demo.domain.value.dto.CourseDTO;
import com.example.demo.domain.value.dto.ResourceDTO;
import com.example.demo.domain.value.dto.create.CreateResourceDTO;
import com.example.demo.domain.value.dto.update.UpdateResourceDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ResourceService {

    List<ResourceDTO> getAllResources(Long lessonId);
    ResourceDTO getResourceById(Long lessonId);
    ResourceDTO createResource(CreateResourceDTO dto,Long id);
    void deleteResource(Long id);

    ResourceDTO updateResource(UpdateResourceDTO dto, Long id);
}
