package com.example.demo.domain.service.impl;


import com.example.demo.config.TokenProvider;
import com.example.demo.domain.entity.Authority;
import com.example.demo.domain.entity.QUser;
import com.example.demo.domain.repository.AuthorityRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.service.UserService;
import com.example.demo.domain.value.UserPrincipal;
import com.example.demo.domain.value.dto.LoginDTO;
import com.example.demo.domain.value.dto.TokenDTO;
import com.example.demo.domain.value.dto.update.UpdateUserDTO;
import com.example.demo.exceptions.InvalidResourceException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.dto.SignUpDTO;
import com.example.demo.domain.value.dto.UserDTO;
import com.example.demo.utility.validator.UserValidator;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final AuthorityRepository authorityRepository;

    @Lazy
    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, AuthorityRepository authorityRepository, TokenProvider tokenProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.authorityRepository = authorityRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserDTO> getAllUsers(Map<String,Object> filterOption){
        //
        try {
            if ( ! hasAdminAccess()){
                throw new InvalidResourceException("Permission denied !");
            }
            QUser qUser = QUser.user;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if (filterOption.get("firstName") != null){
                booleanBuilder.and(qUser.firstName.containsIgnoreCase(String.valueOf(filterOption.get("firstName"))));
            }
            if (filterOption.get("lastName") != null){
                booleanBuilder.and(qUser.lastName.containsIgnoreCase(String.valueOf(filterOption.get("lastName"))));
            }
            if (filterOption.get("email") != null){
                booleanBuilder.and(qUser.email.containsIgnoreCase(String.valueOf(filterOption.get("email"))));
            }
            if (filterOption.get("username") != null){
                booleanBuilder.and(qUser.username.containsIgnoreCase(String.valueOf(filterOption.get("username"))));
            }
            if (filterOption.get("authority") != null){
                booleanBuilder.and(qUser.authority.name.containsIgnoreCase(String.valueOf(filterOption.get("authority"))));
            }

            String order = (filterOption.get("order") == null) ? "ASC": String.valueOf(filterOption.get("order"));
            int pageNumber = (filterOption.get("page") == null) ? 0 :  Integer.valueOf(String.valueOf(filterOption.get("page")));
            int size = (filterOption.get("size") == null) ? 20 : Integer.valueOf(String.valueOf(filterOption.get("size"))) ;
            Sort.Direction direction = ( order.equals("DESC")) ? Sort.Direction.DESC: Sort.Direction.ASC ;
            PageRequest page = PageRequest.of(pageNumber,size, Sort.by(direction,"id"));
            if (booleanBuilder.getValue() != null) {
                return userRepository.findAll(booleanBuilder,page).map(user -> modelMapper.map(user, UserDTO.class));
            }else {
                return userRepository.findAll(page).map(user -> modelMapper.map(user, UserDTO.class));
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new InvalidResourceException(e.getMessage());
        }
    }

    public void signUp(SignUpDTO dto){
        if(userRepository.findUserByEmail(dto.getEmail()).isPresent()){
            throw new InvalidResourceException("Email is already registered");
        }
        if(userRepository.findUserByUsername(dto.getUsername()).isPresent()){
            throw new InvalidResourceException("Username is already used");
        }
        if ( ! UserValidator.validate(dto).isEmpty()){
            throw new InvalidResourceException("Invalid User", UserValidator.validate(dto) );
        }
        try {
            save(dto,null);
        }catch(Exception e){
            throw new InvalidResourceException("Failed to signup !, error: "+e.getMessage());
        }
    }

    public UserDTO getUserById(long id){
        if ( ! hasAccess(id)){
            throw new InvalidResourceException("Permission denied !");
        }
        User user = userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("User not found with id: "+id)
        );
        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found with id:"+id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found with username:"+username));
    }

    public UserDTO updateUser(UpdateUserDTO dto, Long id){
        try {
            if ( ! hasAccess(id)){
                throw new InvalidResourceException("Permission denied !");
            }
            log.info("User Information: "+dto.toString());
            // TODO: validate new data
            User user = getById(id);
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setSummary(dto.getSummary());
            user.setPhone(dto.getPhone());
//            user.getAddress().setAddress1(dto.getAddress1());
//            user.getAddress().setAddress2(dto.getAddress2());
//            user.getAddress().setAddress3(dto.getAddress3());
            if (dto.getAuthority() != null){
                if (hasAdminAccess()){
                    user.setAuthority(authorityRepository.findByName(dto.getAuthority()));
                }else {
                    throw new InvalidResourceException("Permission denied !");
                }
            }
            return modelMapper.map(userRepository.save(user),UserDTO.class);
        }catch (Exception e){
            throw new InvalidResourceException("Permission denied !");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if(!userOptional.isPresent()){
            System.out.println("user not found");
            throw new javax.persistence.EntityNotFoundException("User not found by username: "+username);
        }
        User user=userOptional.get();
        System.out.println("user password is: "+user.getPassword());
        UserPrincipal userPrincipal = new UserPrincipal(user);
        userPrincipal.setAuthorities(List.of(new SimpleGrantedAuthority(user.getAuthority().getName())));
        return userPrincipal;
    }

    @Override
    public UserDTO save(SignUpDTO dto, Long id) {
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        Authority authority = authorityRepository.findByName(dto.getAuthority());
        User user= modelMapper.map(dto,User.class);
        if (id != null ){
            user.setId(id);
        }
        user.setAuthority(authority);
        user.setPassword(encryptedPassword);
        user.setActive(true);
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }

    @Override
    public void delete(Long userId) {
        if ( ! hasAdminAccess()){
            throw new InvalidResourceException("Permission denied !");
        }
        userRepository.delete(getById(userId));
    }

    @Override
    public TokenDTO login(LoginDTO dto) {
        try {
            User user = getUserByUsername(dto.getLogin());
            // user = getUserByUsername(dto.getLogin());
            if ( ! user.isActive() ){
                throw new InvalidResourceException("Account disabled");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), dto.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(String.format("Authentication success for user { login: %s } at %s",dto.getLogin(), LocalDateTime.now().toString()));
            return new TokenDTO(tokenProvider.generateToken(authentication));
        }catch (Exception e){
            throw new InvalidResourceException("Bad credentials");
        }
    }

    @Override
    public boolean hasAccess(Long id) {
        System.out.println(">>> Checking user permission");
        String authenticationUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedUser = getUserByUsername(authenticationUsername);
        final boolean equals = Objects.equals(loggedUser.getId(), id);
        return equals || loggedUser.getAuthority().getName().equals("ADMIN");
    }
    public boolean hasAdminAccess() {
        System.out.println(">>> Checking user permission");
        String authenticationUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedUser = getUserByUsername(authenticationUsername);
        return loggedUser.getAuthority().getName().equals("ADMIN");
    }

    private User getCurrentAuthenticatedUser() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        return userRepository.findUserById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


}
//    getAll(params: any){
//        let queryString = new URLSearchParams(params).toString();// key=value&key2=val2&
//        return this.http.get(`${this.API_ENDPOINT}/users?${queryString}`);
//    }