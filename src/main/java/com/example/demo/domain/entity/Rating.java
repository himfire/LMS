package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rating extends Auditable{

    // Course_ratings :
    // id, stars... , Fk: course_id , user_id
    // 1   5*            1              1
    // 2   4*           1               99
    // 2   1*           2               1

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sumOfStars;
    private String feedback;

    @ManyToOne
    private Course course;

    @ManyToOne
    private User rater;
}
