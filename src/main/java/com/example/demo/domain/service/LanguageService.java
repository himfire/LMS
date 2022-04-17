package com.example.demo.domain.service;

import com.example.demo.domain.entity.Course;
import com.example.demo.domain.entity.Language;
import com.example.demo.domain.value.dto.CourseDTO;
import com.example.demo.domain.value.dto.LanguageDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface LanguageService {

    Page<LanguageDTO> getAllLanguages(Map<String,Object> filterOption);
    List<LanguageDTO> getAllLanguagesByUserId(Long userId);
}
