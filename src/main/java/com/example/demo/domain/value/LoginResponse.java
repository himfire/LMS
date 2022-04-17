package com.example.demo.domain.value;

import com.example.demo.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private User user;
    private String token;
}
