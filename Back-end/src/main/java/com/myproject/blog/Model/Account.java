package com.myproject.blog.Model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ACCOUNT")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name="LASTNAME")
    private String lastName;
    private String email;
    private String password;
    private Boolean gender;
    private Integer age;

    @Column(name = "BIRTHDATE")
    private Date birthDate;
    private String photo;
    private String token;
    private Integer role;
    private String resetPasswordToken;
    private LocalDateTime resetPasswordExpiry;

    @ManyToMany
    @JoinTable(
        name = "ACCOUNT_AUTHORITY",
        joinColumns = @JoinColumn(name = "ID_ACCOUNT"),
        inverseJoinColumns = @JoinColumn(name = "ID_AUTHORITY")
    )
    private Set<Authority> authorities;

}

