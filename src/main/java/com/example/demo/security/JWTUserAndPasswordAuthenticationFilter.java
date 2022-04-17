package com.example.demo.security;


import com.example.demo.domain.entity.User;
import com.example.demo.domain.service.impl.UserServiceImpl;
import com.example.demo.domain.value.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class JWTUserAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final List<String> allowedOrigins = Arrays.asList("http://127.0.0.1:8081","http://localhost:7050", "http://localhost:3000","http://localhost:8080" );

    public JWTUserAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            UsernameAndPasswordAuthenticationRequest authenticationRequest =mapper
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            System.out.println("" +
                    "username is "+authenticationRequest.getUsername()+" and password is: "+
                    authenticationRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            if (authenticate.isAuthenticated()) {
                System.out.println("authentication succeeded");

            }
            System.out.println("authentication succeeded");
            return authenticate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        ApiResponse apiResponse = new ApiResponse(403,"Access denied");
        apiResponse.setMessage("Incorrect username and password");

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,authorization, content-type, xsrf-token");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out,apiResponse);
        out.flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        /*ApiResponse apiResponse = new ApiResponse(200,"Access granted");
        apiResponse.setMessage("logged in successfully");

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,authorization, content-type, xsrf-token");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out,apiResponse);*/

        String key = "b4553af85076db7c6fb5816addc36836a22dd8b63cf587c7032eb297243b5ef801398ffb5e25730d2090a6d9efc6e98f264fdd48016e711ebe7f075ccdb7ed3f";
        UserPrincipal user = (UserPrincipal) authResult.getPrincipal();
        JwtTokenProfile jwtTokenProfile = new JwtTokenProfile();
        User applicationUser = user.getUser();
//jwtTokenProfile.setRole(applicationUser.getRole());
        jwtTokenProfile.setFull_name(applicationUser.getFirstName()+" " +applicationUser.getLastName());
        String token = Jwts.builder()
                .setSubject(applicationUser.getId().toString())
                .claim("authorities", user.getAuthorities())
                .claim("clientId", "client.io")
                .claim("iss", "client.io")
                .claim("profile", jwtTokenProfile)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,authorization, content-type, xsrf-token");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);
        logger.info("the header has " + response.getHeader("Access-Control-Allow-Origin"));
        response.addHeader("Authorization", "Bearer " + token);


        response.setStatus(HttpStatus.OK.value());
        LoginResponse responseObject = new LoginResponse();

        responseObject.setUser(applicationUser);
        responseObject.setToken(token);
        String json = new ObjectMapper().writeValueAsString(responseObject);
        response.getWriter().write(json);
        response.flushBuffer();
    }
}
