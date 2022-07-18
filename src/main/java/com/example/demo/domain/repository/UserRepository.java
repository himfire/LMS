package com.example.demo.domain.repository;


import com.example.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, QuerydslPredicateExecutor<User> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
}
