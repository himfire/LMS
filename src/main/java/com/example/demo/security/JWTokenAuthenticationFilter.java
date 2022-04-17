package com.example.demo.security;

import com.example.demo.domain.value.MESSAGE;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTokenAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JWTokenAuthenticationFilter.class);


    private final List<String> allowedOrigins = Arrays.asList("http://127.0.0.1:8081","http://localhost:7050", "http://localhost:3000","http://localhost:8080" );

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI != null && !requestURI.contains("actuator/health"))
            logger.info("JWTokenAuthenticationFilter process execution to URL {}  from IP Address {} was completed At {}", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());

        if ("OPTIONS".equals(request.getMethod())) {
            // Access-Control-Allow-Origin
            String origin = request.getHeader("Origin");
            logger.info("origin is " + origin);
            response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "yes");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,authorization, content-type, xsrf-token");
            response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            try {
                // 1. Check if the Bearer token is available in the header
                String authorizationHeader = request.getHeader("Authorization");

                // 2.1 Replace the Bearer with empty string
                if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                    MESSAGE.UNAUTHORIZED = "Valid Authorization Header not supplied";
                    logger.info("Valid Authorization Header not supplied");
                    chain.doFilter(request, response); // If not valid, go to the next filter.
                    return;
                }
                String token = authorizationHeader.replace("Bearer ", "");

                // Retrieve the encoded base 64 client secret key from the client id
                String key = "b4553af85076db7c6fb5816addc36836a22dd8b63cf587c7032eb297243b5ef801398ffb5e25730d2090a6d9efc6e98f264fdd48016e711ebe7f075ccdb7ed3f";
                Claims claims =
                        Jwts.parser()
                                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                                .parseClaimsJws(token)
                                .getBody();

                String tokenIssuer = claims.getIssuer();


                String tokenSubject = claims.getSubject();
                if (tokenSubject == null || tokenSubject.isEmpty()) {
                    MESSAGE.UNAUTHORIZED = "Token does not carry a valid subject";
                    logger.info("Token does not carry a valid subject");
                    chain.doFilter(request, response); // If not valid, go to the next filter.
                    return;
                }


                // Get Authorities
                // 4. Set the security Context
                List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("authorities");

                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(tokenSubject, null, simpleGrantedAuthorities);

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                logger.error("Exception ",e);
                SecurityContextHolder.clearContext();
                MESSAGE.UNAUTHORIZED = "Token has expired or invalid signature";

                logger.info("Token has expired or invalid signature");
            }
            chain.doFilter(request, response);
        }
    }

}