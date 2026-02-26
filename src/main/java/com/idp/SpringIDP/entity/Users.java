package com.idp.SpringIDP.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users_tbl")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "company_id")
    private String companyID;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "role")
    private String role;

    @Column(nullable = false, name = "created_at")
    private String createdAt;

    @Column(nullable = false, name = "archive")
    private int archive;
}
