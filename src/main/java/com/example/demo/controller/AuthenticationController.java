package com.example.demo.controller;

import com.example.demo.domain.service.UserService;
import com.example.demo.domain.service.impl.CourseServiceImpl;
import com.example.demo.domain.service.impl.UserServiceImpl;
import com.example.demo.domain.value.dto.LoginDTO;
import com.example.demo.domain.value.dto.SignUpDTO;
import com.example.demo.domain.value.dto.TokenDTO;
import com.example.demo.domain.value.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
@CrossOrigin("*")
public class AuthenticationController {


    private final UserService userService;
    private final CourseServiceImpl courseService;

    public AuthenticationController(UserService userService, CourseServiceImpl courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO dto){
        userService.signUp(dto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto){
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }

    @PostMapping("/verify/{userId}")
    public ResponseEntity<?> verifyUser(@PathVariable Long userId, @RequestParam String code){
        userService.verifyUser(userId,code);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
