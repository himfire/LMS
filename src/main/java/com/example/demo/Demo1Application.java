package com.example.demo;

import com.example.demo.domain.entity.Authority;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.AuthorityRepository;
import com.example.demo.domain.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class Demo1Application {
//user,teacher,employee,admin


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }


}
