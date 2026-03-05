package com.idp.SpringIDP.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "items_tbl")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "document_tbl_id")
    private int documentTableID;

    private String pallet;

    @Column(name = "handling_unit")
    private String handlingUnit;

    @Column(name = "package_type")
    private String packageType;

    private String pieces;
    private String description;
    private String weight;

    @Column(name = "class")
    private String clss;

    private String nmfc;
    private String dimension;

    @Column(nullable = false)
    private int archive;
}
