package com.example.demo.domain.service.impl;

import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.repository.CourseTagsRepository;
import com.example.demo.domain.service.CourseTagsService;
import com.example.demo.domain.value.dto.ClassificationDTO;
import com.example.demo.domain.value.dto.TagDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseTagsServiceImpl implements CourseTagsService {

    private final CourseTagsRepository courseTagsRepository;
    private final ModelMapper modelMapper;

    public CourseTagsServiceImpl(CourseTagsRepository classificationRepository, ModelMapper modelMapper) {
        this.courseTagsRepository = classificationRepository;
        this.modelMapper = modelMapper;
    }

    public List<TagDTO> getAllTagsByName(String name){
        return courseTagsRepository.findByNameContainsIgnoreCase(name).stream().map(courseTag -> modelMapper.map(
                courseTag, TagDTO.class)).collect(Collectors.toList());
    }

    public ClassificationDTO getCourseTagsById(long id){
        Tag tag = courseTagsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Course Tag not found by Id: "+id)
        );
        return modelMapper.map(tag,ClassificationDTO.class);
    }

    public TagDTO createTag(TagDTO dto){
        return modelMapper.map(courseTagsRepository.save(modelMapper.map(dto, Tag.class)), TagDTO.class);
    }

    @Override
    public Tag getTagById(Long id) {
        return courseTagsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course Tag not found by Id:"+id));
    }

    public void deleteTag(Long id){
        Tag tag = courseTagsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Course Tag not found by Id: "+id));
        courseTagsRepository.delete(tag);
    }

    public TagDTO updateCourseTag(TagDTO dto, Long id){
        Tag tagToUpdate = courseTagsRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Course Tag not found by Id: "+id));
        return modelMapper.map(courseTagsRepository.save(modelMapper.map(dto, Tag.class)), TagDTO.class);
    }


}
