package com.example.demo.domain.entity;

import com.example.demo.domain.value.enumurator.ResourceType;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "resources")
public class Resource extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    private ResourceType type;

    @Column
    private String url;

    @Column
    private String description;

    @ManyToOne
    private Lesson lesson;
}
