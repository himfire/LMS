package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Course;
import com.example.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> , QuerydslPredicateExecutor<Course> {
    boolean existsBySlugTitle(String slugTitle);
List<Course> findAllByTitle(String title);
Optional<Course> findBySlugTitle(String slugTitle);
List<Course> findByAuthor(String author);
}
