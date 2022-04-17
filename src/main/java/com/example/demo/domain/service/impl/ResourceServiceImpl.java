package com.example.demo.domain.service.impl;

import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.entity.Resource;
import com.example.demo.domain.repository.ResourceRepository;
import com.example.demo.domain.service.LessonService;
import com.example.demo.domain.service.ResourceService;
import com.example.demo.domain.value.dto.LessonDTO;
import com.example.demo.domain.value.dto.ResourceDTO;
import com.example.demo.domain.value.dto.create.CreateResourceDTO;
import com.example.demo.domain.value.dto.update.UpdateResourceDTO;
import com.example.demo.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

//    @Autowired
//    private AWSService awsService;
    private final ModelMapper modelMapper;
    private final ResourceRepository resourceRepository;
    private final LessonService lessonService;

    public ResourceServiceImpl(ModelMapper modelMapper, ResourceRepository resourceRepository, LessonService lessonService) {
        this.modelMapper = modelMapper;
        this.resourceRepository = resourceRepository;
        this.lessonService = lessonService;
    }

    @Override
    public List<ResourceDTO> getAllResources(Long lessonId) {
        Lesson lesson = lessonService.getById(lessonId);
        return resourceRepository.findByLesson(lesson).stream().map(resource -> modelMapper.map(resource,ResourceDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ResourceDTO getResourceById(Long id) {
        return modelMapper.map(resourceRepository
                .findById(id).orElseThrow(
                        () ->
                                new EntityNotFoundException("Resource not found by id: "+id)
                ),
                ResourceDTO.class);
    }

    @Override
    public ResourceDTO createResource(CreateResourceDTO dto, Long lessonId) {
        //TODO: API Store resource -> url
        Lesson lesson = lessonService.getById(lessonId);
        final Resource resource = Resource
                .builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .url("url")
                .lesson(lesson)
                .build();

        return modelMapper.map(resourceRepository.save(resource),ResourceDTO.class);
    }

    private Resource getById(Long id) {
        return resourceRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Resource not found with id: "+id)
        );

    }

    @Override
    public void deleteResource(Long id) {
        //TODO: call API to delete resource
        Resource resource = getById(id);
        resourceRepository.delete(resource);
    }

    @Override
    public ResourceDTO updateResource(UpdateResourceDTO dto, Long id) {
        Resource resourceToUpdate = getById(id);
        resourceToUpdate.setName(dto.getName());
        resourceToUpdate.setDescription(dto.getDescription());
        //TODO: isValid(lessonToUpdate)
        return modelMapper.map(resourceRepository.save(resourceToUpdate),ResourceDTO.class);
    }

}
