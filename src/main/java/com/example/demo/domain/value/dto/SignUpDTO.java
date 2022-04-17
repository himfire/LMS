package com.example.demo.domain.value.dto;

import com.sun.istack.NotNull;
import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String phone;

    private String authority;
}
