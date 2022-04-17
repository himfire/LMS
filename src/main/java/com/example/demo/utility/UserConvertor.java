package com.example.demo.utility;


import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConvertor {

    @Autowired
    private static ModelMapper modelMapper;

    public UserConvertor(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static List<UserDTO> convertListOfEntityToDTO(List<User> users) {
        return users.stream().map(
                user -> modelMapper.map(user,UserDTO.class)
        ).collect(Collectors.toList());
    }
}
