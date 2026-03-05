package com.idp.SpringIDP.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stored_images_tbl")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "image_name")
    private String imageName;

    @Column(nullable = false, name = "status")
    private int Status;

    @Column(nullable = false, name = "ai_response")
    private int aiResponse;

    @Column(nullable = false, name = "stored_date")
    private String storedDate;

    @Column(nullable = false)
    private int archive;
}
