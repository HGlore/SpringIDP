package com.idp.SpringIDP.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "production_tbl")
public class Production {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "document_tbl_id")
    private int documentTableID;

    @Column(nullable = false, name = "company_id")
    private String companyID;

    @Column(nullable = false, name = "production_date")
    private String productionDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(nullable = false, name = "start_time")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(nullable = false, name = "end_time")
    private LocalTime endTime;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, name = "created_at")
    private LocalTime createdAt;
}
