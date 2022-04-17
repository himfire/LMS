package com.example.demo.utility;

import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.value.dto.LessonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class LessonConvertor {

    @Autowired
    private static ModelMapper modelMapper;

    public LessonConvertor(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static List<LessonDTO> convertListOfEntityToDTO(List<Lesson> lessons) {
        return lessons.stream().map(
                lesson -> modelMapper.map(lesson, LessonDTO.class)
        ).collect(Collectors.toList());
    }
}
