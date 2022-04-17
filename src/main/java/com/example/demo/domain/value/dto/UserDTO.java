package com.example.demo.domain.value.dto;

import com.example.demo.domain.entity.Authority;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String summary;

    private String phone;

    private String address1;
    private String address2;
    private String address3;

    //TODO: change to Entity
    private Set<Long> languages;

    private Authority authority;
}
