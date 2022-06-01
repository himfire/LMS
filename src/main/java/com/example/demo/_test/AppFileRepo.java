package com.example.demo._test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppFileRepo extends JpaRepository<AppFile,Long> {
    boolean existsByFileName(String fileName);
}
