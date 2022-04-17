package com.example.demo.utility;

import com.example.demo.domain.entity.LessonModule;
import com.example.demo.domain.value.dto.LessonModuleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class LessonsModuleConvertor {

    @Autowired
    private static ModelMapper modelMapper;

    public LessonsModuleConvertor(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static List<LessonModuleDTO> convertListOfEntityToDTO(List<LessonModule> lessonsModule) {
        return lessonsModule.stream().map(
                user -> modelMapper.map(lessonsModule, LessonModuleDTO.class)
        ).collect(Collectors.toList());
    }
}
