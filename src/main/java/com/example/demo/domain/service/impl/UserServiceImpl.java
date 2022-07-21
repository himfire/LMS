package com.example.demo.domain.service.impl;


import com.example.demo.config.TokenProvider;
import com.example.demo.domain.entity.*;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.repository.VerificationRequestRepository;
import com.example.demo.domain.service.MailService;
import com.example.demo.domain.service.UserService;
import com.example.demo.domain.value.UserPrincipal;
import com.example.demo.domain.value.dto.LoginDTO;
import com.example.demo.domain.value.dto.TokenDTO;
import com.example.demo.domain.value.dto.update.UpdateUserDTO;
import com.example.demo.domain.value.dto.SignUpDTO;
import com.example.demo.domain.value.dto.UserDTO;
import com.example.demo.domain.value.enumurator.Authority;
import com.example.demo.exceptions.CustomException;
import com.example.demo.utility.validator.SignUpValidator;
import com.example.demo.utility.validator.UserValidator;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;


@Service("userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Lazy
    private final TokenProvider tokenProvider;
    private final VerificationRequestRepository verificationRequestRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, TokenProvider tokenProvider, VerificationRequestRepository verificationRequestRepository, MailService mailService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.tokenProvider = tokenProvider;
        this.verificationRequestRepository = verificationRequestRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserDTO> getAllUsers(Map<String,Object> filterOption){
        //
        try {
            if ( ! hasAdminAccess()){
                throw CustomException.builder()
                        .code("Permission denied !")
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
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
                booleanBuilder.and(qUser.authority.eq(Authority.valueOf((String) filterOption.get("authority"))));
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
            throw CustomException.builder()
                    .code(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public void signUp(SignUpDTO dto){
        List<String> errors = new ArrayList<String>();
        dto.setAuthority(Authority.USER);
        if(userRepository.existsByEmail(dto.getEmail())){
            errors.add("Email is already registered");
        }
        if(userRepository.existsByUsername(dto.getUsername())){
            errors.add("Username is already registered");
        }
        if(! errors.isEmpty()){
            throw CustomException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .code("Error in completing registration")
                    .errors(errors)
                    .build();

        }
        if ( ! SignUpValidator.validate(dto).isEmpty()){
            throw CustomException.builder()
                    .code("Invalid User")
                    .errors(SignUpValidator.validate(dto))
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        try {
            final User savedUser = User.builder()
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .authority(dto.getAuthority())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .authority(dto.getAuthority())
                    .build();
            // send a verif code to email address

            final User saved = userRepository.save(savedUser);
            String code = UUID.randomUUID().toString();
            /*mailService.sendEmail(dto.getEmail(),"Acount verification",
                    "Your verifictaion code is "+ code

                    +"Or you can confirm your account by clicking here : "
                            + "http://20.237.84.70:80/verify-account/"+saved.getId()+"/"+code
                    );*/
            verificationRequestRepository.save(VerificationRequest.builder()
                    .code(code)
                    .user(savedUser)
                    .failedAttemps(0L)
                    .build());
        }catch(Exception e){
            throw CustomException.builder()
                    .code("Failed to signup !, error: "+e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public UserDTO getUserById(long id){
//        if ( ! hasAccess(id)){
//            throw CustomException.builder()
//                    .code("Permission denied !")
//                    .status(HttpStatus.BAD_REQUEST)
//                    .build();
//        }
        User user = userRepository.findUserById(id).orElseThrow(
                ()-> new EntityNotFoundException("User not found with id: "+id)
        );
        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public User getById(long id) {
        return userRepository.findUserById(id).orElseThrow(()-> new EntityNotFoundException("User not found with id:"+id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found with username:"+username));
    }

    public UserDTO updateUser(UpdateUserDTO dto, Long id){
        try {
            if ( ! hasAccess(id)){
                  throw CustomException.builder()
                        .code("Permission denied !")
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }
            log.info("User Information: "+dto.toString());
            if ( ! UserValidator.validate(dto).isEmpty()){
                throw CustomException.builder()
                        .code("Invalid User")
                        .errors(UserValidator.validate(dto))
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }
            // TODO: validate new data
            User user = getById(id);
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setDescription(dto.getSummary());
            user.setPhone(dto.getPhone());
            if (dto.getAuthority() != null){
                if (hasAdminAccess()){
                    user.setAuthority(dto.getAuthority());
                }else {
                    throw CustomException.builder()
                            .code("Permission denied !")
                            .status(HttpStatus.BAD_REQUEST)
                            .build();
                }
            }
            return modelMapper.map(userRepository.save(user),UserDTO.class);
        }catch (Exception e){
            throw CustomException.builder()
                    .code("Permission denied !")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
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
        userPrincipal.setAuthorities(List.of(new SimpleGrantedAuthority(user.getAuthority().toString())));
        return userPrincipal;
    }

    @Override
    public UserDTO save(SignUpDTO dto, Long id) {
        if(userRepository.findUserByEmail(dto.getEmail()).isPresent()){
            throw CustomException.builder()
                    .code("User is already register with email address: "+dto.getEmail())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }
        if(userRepository.findUserByUsername(dto.getUsername()).isPresent()){
            throw CustomException.builder()
                    .code("User is already register with email address: "+dto.getEmail())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        User user= modelMapper.map(dto,User.class);
        if (id != null ){
            user.setId(id);
        }
        user.setAuthority(Authority.USER);
        user.setPassword(encryptedPassword);
        user.setActive(true);
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }

    @Override
    public void delete(Long userId) {
        if ( ! hasAdminAccess()){

            throw CustomException.builder()
                    .code("Permission denied !")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        userRepository.delete(getById(userId));
    }

    @Override
    public TokenDTO login(LoginDTO dto) {
        try {
            User user = getUserByUsername(dto.getLogin());
            // user = getUserByUsername(dto.getLogin());
            if ( ! user.isActive() ){

                throw CustomException.builder()
                        .code("Account disabled !")
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), dto.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(String.format("Authentication success for user { login: %s } at %s",dto.getLogin(), LocalDateTime.now().toString()));
            return new TokenDTO(tokenProvider.generateToken(authentication));
        }catch (Exception e){
            throw CustomException.builder()
                    .code("Bad credentials !")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    private boolean hasAnyAccess(Long userID, List<Authority> nauthorities){
        return  true;
    }
    /*****
     * This method will check that tha current authenticated user has an id = @param userId or is an admin
     * @param userId
     * @return
     */
    @Override
    public boolean hasAccess(Long userId) {
        try {
            System.out.println(">>> Checking user permission");
            String authenticationUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            User loggedUser = getUserByUsername(authenticationUsername);
            final boolean equals = Objects.equals(loggedUser.getId(), userId);
            return equals || loggedUser.getAuthority().equals(Authority.ADMIN);
        }
        catch (Exception e){
            throw CustomException.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .code("Permission denied")
                    .build();
        }
    }

    @Override
    public void verifyUser(Long userId, String code) {
        User user = getById(userId);
        if (verificationRequestRepository.existsByUserId(userId)){
            final VerificationRequest verificationRequest = verificationRequestRepository.findByUserId(userId);
            if (verificationRequest.getCode().equals(code) ){
                user.setActive(true);
                userRepository.save(user);
                verificationRequestRepository.delete(verificationRequest);
            }else{
                Long failedAttemps = verificationRequest.getFailedAttemps() +1 ;
                verificationRequest.setFailedAttemps(failedAttemps);
                verificationRequestRepository.save(verificationRequest);
                throw CustomException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code("Verification failed, make sure you provide a valid code !")
                        .build();
            }
        }

    }

    public boolean hasAdminAccess() {
        System.out.println(">>> Checking user permission");
        String authenticationUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedUser = getUserByUsername(authenticationUsername);
        return loggedUser.getAuthority().equals(Authority.ADMIN);
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