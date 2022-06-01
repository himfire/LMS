package com.example.demo.domain.repository;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRequestRepository extends JpaRepository<VerificationRequest,Long> {
    VerificationRequest findByUserId(Long userId);
    VerificationRequest findByUser(User user);
    boolean existsByUserId(Long userId);
    boolean existsByUser(User user);
}
