package com.example.demo.domain.value.dto.create;

import lombok.Data;

@Data
public class CreateLessonDTO {

    private String title;
    private String content;
    private String duration;
    private Long lessonModuleId;
}
