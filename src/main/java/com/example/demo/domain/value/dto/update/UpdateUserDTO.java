package com.example.demo.domain.value.dto.update;

import com.example.demo.domain.entity.Authority;
import lombok.Data;

@Data
public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private String summary;
    private String phone;

    private String address1;
    private String address2;
    private String address3;
    private String authority;
}
