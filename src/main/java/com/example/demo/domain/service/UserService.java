package com.example.demo.domain.service;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.UserPrincipal;
import com.example.demo.domain.value.dto.LoginDTO;
import com.example.demo.domain.value.dto.SignUpDTO;
import com.example.demo.domain.value.dto.TokenDTO;
import com.example.demo.domain.value.dto.UserDTO;
import com.example.demo.domain.value.dto.update.UpdateUserDTO;
import com.stripe.model.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    Page<UserDTO> getAllUsers(Map<String,Object> filterOption);
    void signUp(SignUpDTO dto);
    UserDTO getUserById(long id);
    User getById(long id);
    User getUserByUsername(String username);
    UserDTO updateUser(UpdateUserDTO dto, Long id);
    UserDTO save(SignUpDTO admin, Long id);
    void delete(Long userId);
    TokenDTO login(LoginDTO dto);
    boolean hasAccess(Long id);
}
