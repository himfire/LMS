package com.example.demo.domain.repository;

import com.example.demo.domain.entity.LessonModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonsModuleRepository extends JpaRepository<LessonModule,Long> , QuerydslPredicateExecutor<LessonModule> {
}
