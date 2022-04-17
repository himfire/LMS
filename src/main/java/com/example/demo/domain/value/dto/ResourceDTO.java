package com.example.demo.domain.value.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {

    private Long id;

    private String name;

    private String url;

    private String description;
}
