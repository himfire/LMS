package com.example.demo.domain.entity;

import com.example.demo.domain.value.enumurator.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "UserSubscriptions")
public class UserSubscription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User subscribedUser;

    @ManyToOne
    private Course course;

    private LocalDate startDate;

    private LocalDate endDate;

    private Status status;

    private Long rating;

    private String comment;
}
