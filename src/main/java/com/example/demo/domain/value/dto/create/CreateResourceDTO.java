package com.example.demo.domain.value.dto.create;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateResourceDTO {
    private String name;
    private String description;
    private MultipartFile file;
}
