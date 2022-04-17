package com.example.demo.domain.entity;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.LastModifiedDate;

import org.joda.time.DateTime;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sys_users")
@Data
public class User extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    private String summary;

    @Column(name = "password_modified_date",length = 10)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime passwordModifiedDate;

    private String phone;

    @Embedded
    private Address address;

    @Column(name = "email_verified")
    private boolean emailAddressVerified;

    @Column(name = "last_login_date",length = 10)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastLoginDate;

    private boolean active;

    @Column(name = "loginIP",length = 30)
    private String loginIP;

    @Column(name = "failed_attemps",length = 4)
    private Long failedLoginAttempts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_languages",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id")
    )
    private Set<Language> languages;

    private String urlImage;

    @OneToOne
    private Authority authority;

    @OneToMany(mappedBy = "author")
    private List<Course> courses;
}
