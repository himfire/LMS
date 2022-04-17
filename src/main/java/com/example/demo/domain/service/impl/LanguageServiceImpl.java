package com.example.demo.domain.service.impl;


import com.example.demo.domain.entity.Language;
import com.example.demo.domain.repository.LanguageRepository;
import com.example.demo.domain.service.LanguageService;
import com.example.demo.domain.value.dto.CourseDTO;
import com.example.demo.domain.value.dto.LanguageDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LanguageServiceImpl implements LanguageService {

    private final ModelMapper modelMapper;
    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(ModelMapper modelMapper, LanguageRepository languageRepository) {
        this.modelMapper = modelMapper;
        this.languageRepository = languageRepository;
    }

    @Override
    public Page<LanguageDTO> getAllLanguages(Map<String, Object> filterOption) {
        return null;
    }

    @Override
    public List<LanguageDTO> getAllLanguagesByUserId(Long userId) {
        return null;
    }
}
