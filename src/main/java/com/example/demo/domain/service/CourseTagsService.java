package com.example.demo.domain.service;

import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.value.dto.TagDTO;
import java.util.List;

public interface CourseTagsService {

    List<TagDTO> getAllTagsByName(String name);
    TagDTO createTag(TagDTO dto);
    Tag getTagById(Long id);
    void deleteTag(Long id);
    TagDTO updateCourseTag(TagDTO dto, Long id);
}
