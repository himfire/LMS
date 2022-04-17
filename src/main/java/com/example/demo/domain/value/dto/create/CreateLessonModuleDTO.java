package com.example.demo.domain.value.dto.create;

import lombok.Data;

@Data
public class CreateLessonModuleDTO {
    private String title;
    private String description;
    private Long courseId;
}
