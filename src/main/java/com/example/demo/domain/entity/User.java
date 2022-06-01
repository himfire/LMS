package com.example.demo.domain.entity;

import com.example.demo.domain.value.enumurator.Authority;
import com.example.demo.domain.value.enumurator.Verification;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sys_users")
@Data
public class User extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String username;
    private String password;

    @Embedded
    private Phone phone;

    @Embedded
    private Address address;
    private Verification verificationStatus;

    private boolean isActive;
    private boolean isNotLocked;
    private String profileImageUrl;
    private Authority authority;

    @OneToMany(mappedBy = "author")
    private List<Course> courses;

    private String description;
    private LocalDateTime lastLoginDate;
    private String loginIP;
}
