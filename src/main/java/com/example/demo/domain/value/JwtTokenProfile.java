package com.example.demo.domain.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtTokenProfile {
    private String role;
    private String full_name;
}