package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language,Long> , QuerydslPredicateExecutor<Language> {
}
