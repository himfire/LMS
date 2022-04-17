package com.example.demo.domain.value.dto;

import com.example.demo.domain.entity.Lesson;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class LessonModuleDTO {

    private Long id;

    private String title;

    private String description;

    private List<Lesson> lesson;
}
