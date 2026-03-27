package com.idp.SpringIDP.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int status = 1;

    @Column(nullable = false)
    private String comment = "NO COMMENT";

    @Column(nullable = false)
    private int archive;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
