package com.example.demo.domain.value.dto;

import com.example.demo.domain.entity.Address;
import com.example.demo.domain.entity.Phone;
import com.example.demo.domain.value.enumurator.Authority;
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

    private Phone phone;

    private Address address;

    private Authority authority;
}
