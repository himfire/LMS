package com.example.demo.domain.value.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class LanguageDTO {

    @NotNull(message = "Language name should not be null")
    @NotBlank(message="Language name should not be blank")
    private String name;

    @NotNull(message = "Language code should not be null")
    @NotBlank(message="Language code should not be blank")
    private String code;
}
