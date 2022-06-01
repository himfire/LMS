package com.example.demo.config;

import com.example.demo.domain.entity.Phone;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.service.UserService;
import com.example.demo.domain.value.enumurator.Authority;
import com.example.demo.domain.value.enumurator.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ApplicationInitializer {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void init(){
        User admin = User.builder()
                .id(1L)
                .firstName("Admin")
                .lastName("Admin")
                .username("admin")
                .email("admin@admin.com")
                .password("123")
                .phone(Phone.builder()
                        .countryCode("244")
                        .phoneNumber("24232244342L")
                        .build())
                .authority(Authority.ADMIN)
                .isActive(true)
                .verificationStatus(Verification.ACTIVE)
                .build();
        userRepository.save(admin);
    }
}
