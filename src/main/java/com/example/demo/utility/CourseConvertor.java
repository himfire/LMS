package com.example.demo.utility;

import com.example.demo.domain.entity.Course;
import com.example.demo.domain.value.dto.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseConvertor {
    @Autowired
    private static ModelMapper modelMapper;

    public CourseConvertor(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public static List<CourseDTO> convertListOfEntityToDTO(List<Course> classrooms) {
        return classrooms.stream().map(
                classroom -> modelMapper.map(classrooms, CourseDTO.class)
        ).collect(Collectors.toList());
    }
}
