package com.idp.SpringIDP.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "totals_tbl")
public class Totals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total_pallet_count")
    private String totalPalletCnt;

    @Column(name = "total_handling_unit")
    private String totalHandlingUnit;

    @Column(name = "total_pieces")
    private String totalPieces;

    @Column(name = "total_weight")
    private String totalWeight;

    @Column(nullable = false)
    private int archive;
}
