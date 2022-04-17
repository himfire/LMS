package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Lesson;
import com.example.demo.domain.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Long>, QuerydslPredicateExecutor<Resource> {
    List<Resource> findByLesson(Lesson lesson);
}
