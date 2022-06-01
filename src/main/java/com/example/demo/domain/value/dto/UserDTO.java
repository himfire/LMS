package com.example.demo.domain.value.dto;

import com.example.demo.domain.entity.Address;
import com.example.demo.domain.entity.Phone;
import com.example.demo.domain.value.enumurator.Authority;
import lombok.*;


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

    private Phone phone;

    private Address address;

    private boolean isActive;

    private Authority authority;
}
