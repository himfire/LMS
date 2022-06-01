package com.example.demo.utility.validator;

import com.example.demo.domain.value.dto.SignUpDTO;
import com.example.demo.domain.value.enumurator.Authority;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static List<String> validate(SignUpDTO signUpDTO){
        List<String> errors =  new ArrayList<String>();
        if(signUpDTO.getEmail() == null ){
            errors.add("Email is required");
        } else if ( ! signUpDTO.getEmail().matches("^[a-zA-Z]+[A-Za-z0-9+_.-]{3,}+@(.+)$")) {
            // fitsname.lastname@company.com
            errors.add("Email is invalid");
        }
        if(signUpDTO.getFirstName() == null ){
            errors.add("First Name must not be empty");
        } else if ( ! signUpDTO.getFirstName().matches("^[a-zA-Z]{3,}")) {
            errors.add("FirstName is invalid");
        }
        if(signUpDTO.getLastName() == null ){
            errors.add("Last Name must not be empty");
        } else if ( ! signUpDTO.getLastName().matches("^[a-zA-Z]{3,}")) {
            errors.add("Last Name is invalid");
        }
        if(signUpDTO.getUsername() == null ){
            errors.add("Username must not be empty");
        } else if ( ! signUpDTO.getUsername().matches("^[a-zA-Z0-9_£$]{3,}")) {
            errors.add("Username is invalid");
        }
        if(signUpDTO.getPassword() == null ){
            errors.add("Password must not be empty");
        } else if ( ! signUpDTO.getPassword().matches("^[a-zA-Z0-9]{6,}$")) {
            errors.add("Password is invalid");
        }
        if(signUpDTO.getPhone() == null ){
            errors.add("Phone must not be empty");
        } else if ( ! signUpDTO.getPhone().getPhoneNumber().matches("[0-9]{7,11}")) {
            errors.add("Phone is invalid");
        }
        if(signUpDTO.getAuthority() == null ){
            errors.add("Authority is required");
        } else if ( signUpDTO.getAuthority().equals(Authority.ADMIN)) {
            errors.add("Invalid authority");
        }

        return errors;
    }
}