package com.example.demo.domain.value.dto.update;

import com.example.demo.domain.entity.Tag;
import com.example.demo.domain.value.enumurator.TagAction;
import lombok.Data;

@Data
public class UpdateCourseTagsDTO {

    private TagAction action;// type: ADD, REMOVE
    private Long tagId;
}
