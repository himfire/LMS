package com.example.demo.controller;

import com.example.demo.domain.service.UserService;
import com.example.demo.domain.service.impl.CourseServiceImpl;
import com.example.demo.domain.service.impl.UserServiceImpl;
import com.example.demo.domain.value.dto.SignUpDTO;
import com.example.demo.domain.value.dto.UserDTO;
import com.example.demo.domain.value.dto.update.UpdateUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
@CrossOrigin("*")
public class UserController {

    Logger _log = LoggerFactory.getLogger(UserController.class);
    @Qualifier("userService")
    private final UserService userService;
    private final CourseServiceImpl courseService;

    public UserController(UserServiceImpl userService, CourseServiceImpl courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('Role_ADMIN')")
    public ResponseEntity<?> getAllUsers(@RequestParam Map<String, Object> queries){
        return new ResponseEntity<>(userService.getAllUsers(queries), HttpStatus.OK);
    }

//    @GetMapping("/search")
    //@PreAuthorize("hasRole('Role_ADMIN')")
    public Page<UserDTO> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size,
                                     @RequestParam(defaultValue ="ASC") String order,
                                     @RequestParam(required = false) String firstName,
                                     @RequestParam(required = false) String lastName,
                                     @RequestParam(required = false) String username,
                                     @RequestParam(required = false) String email,
                                     @RequestParam(required = false) String authority ) {
        final Map<String, Object> queries = Map.of(
                "page", page,
                "size", size,
                "order", order,
                "firstName", firstName,
                "lastName", lastName,
                "username", username,
                "email", email,
                "authority", authority
        );
        return userService.getAllUsers(queries);
    }

    @GetMapping("/search")
    public Page<UserDTO> getAllUsers2(@RequestParam Map<String, Object> queries) {
        return userService.getAllUsers(queries);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userService.hasAccess(#id)") // AOP
    public ResponseEntity getById(@PathVariable Long id){
        return new ResponseEntity(userService.getUserById(id),HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody SignUpDTO dto){
        userService.signUp(dto);
        return new ResponseEntity<>("User created !",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody UpdateUserDTO dto, @PathVariable Long id){
        return new ResponseEntity(userService.updateUser(dto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }
}
