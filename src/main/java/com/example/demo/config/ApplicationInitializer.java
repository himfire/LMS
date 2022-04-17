package com.example.demo.config;

import com.example.demo.domain.entity.Authority;
import com.example.demo.domain.repository.AuthorityRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.service.UserService;
import com.example.demo.domain.value.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationInitializer {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Bean
    public void init(){
        Authority userAuthority = Authority.builder().name("USER").build();
        Authority adminAuthority = Authority.builder().name("ADMIN").build();
        Authority teacherAuthority = Authority.builder().name("TEACHER").build();
        Authority employeeAuthority = Authority.builder().name("EMPLOYEE").build();

        authorityRepository.save(userAuthority);
        authorityRepository.save(adminAuthority);
        authorityRepository.save(teacherAuthority);
        authorityRepository.save(employeeAuthority);
        SignUpDTO admin = SignUpDTO.builder()
                .firstName("Admin")
                .lastName("Admin")
                .username("sys_admin")
                .email("admin@sosadmin.com")
                .password("00000000")
                .phone("+000000000000")
                .authority("ADMIN")
                .build();
        userService.save(admin, 1L);
    }
}
