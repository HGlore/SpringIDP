package com.idp.SpringIDP.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shipper_tbl")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "contact_name")
    private String contactName;

    private String phone;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    private String city;
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(nullable = false)
    private int archive;
}
