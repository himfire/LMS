package com.example.demo.utility.validator;

import com.example.demo.domain.value.dto.update.UpdateUserDTO;
import com.example.demo.domain.value.enumurator.Authority;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static List<String> validate(UpdateUserDTO dto){
        List<String> errors =  new ArrayList<String>();
        if(dto.getFirstName() == null ){
            errors.add("First Name must not be empty");
        } else if ( ! dto.getFirstName().matches("^[a-zA-Z]{3,}")) {
            errors.add("FirstName is invalid");
        }
        if(dto.getLastName() == null ){
            errors.add("Last Name must not be empty");
        } else if ( ! dto.getLastName().matches("^[a-zA-Z]{3,}")) {
            errors.add("Last Name is invalid");
        }
        if(dto.getPhone() == null ){
            errors.add("Phone must not be empty");
        } else if ( ! dto.getPhone().getPhoneNumber().matches("[0-9]{7,11}")) {
            errors.add("Phone is invalid");
        }
        if(dto.getAuthority() == null ){
            errors.add("Authority is required");
        } else if ( dto.getAuthority().equals(Authority.ADMIN)) {
            errors.add("Invalid authority");
        }

        return errors;
    }
}