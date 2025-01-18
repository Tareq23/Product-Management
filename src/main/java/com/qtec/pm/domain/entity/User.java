package com.qtec.pm.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    @Email
    private String email;

    @Column(name = "password", nullable = true, length = 100)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerId")
    private List<Order> orders;

}
