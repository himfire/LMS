package com.example.demo.domain.value.dto.update;

import com.example.demo.domain.entity.Address;
import com.example.demo.domain.entity.Phone;
import com.example.demo.domain.value.enumurator.Authority;
import lombok.Data;

@Data
public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private String summary;
    private Phone phone;

    private Address address;
    private Authority authority;
}
