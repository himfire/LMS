package com.example.demo.domain.repository;

import com.example.demo.domain.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription,Long> , QuerydslPredicateExecutor<UserSubscription> {
}
