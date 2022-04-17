package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.entity.LessonModule;
import com.example.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long>, QuerydslPredicateExecutor<Lesson> {

    List<Lesson> findByLessonsModule(LessonModule lessonModuleId);
}
