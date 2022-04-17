package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseTagsRepository extends JpaRepository<Tag,Long> , QuerydslPredicateExecutor<Tag> {
     List<Tag> findByNameContainsIgnoreCase(String name);
}
