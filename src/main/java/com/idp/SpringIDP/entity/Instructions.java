package com.idp.SpringIDP.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "instructions_tbl")
public class Instructions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;
    private String line;

    @Column(nullable = false)
    private int archive;
}
