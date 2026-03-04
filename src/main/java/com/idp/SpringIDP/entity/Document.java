package com.idp.SpringIDP.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "document_tbl")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Id
    @Column(nullable = false, name = "stored_images_tbl_id")
    private int storedImageTableID;

    @Column(nullable = true, name = "company_id")
    private String companyID;

    @Column(nullable = true, name = "start_time")
    private String startTime;

    @Column(nullable = true, name = "end_time")
    private String endTime;

    @Column(nullable = true, name = "image")
    private String image;

    @Column(nullable = true, name = "account_type")
    private String accountType;

    @Column(nullable = true, name = "detected_acc_type")
    private String detectedAccType;

    @Column(nullable = true, name = "bol_number")
    private String bolNumber;

    @Column(nullable = true, name = "master_bol_number")
    private String masterBolNumber;

    @Column(nullable = true, name = "po_number")
    private String poNumber;

    @Column(nullable = true, name = "quote_number")
    private String quoteNumber;

    @Column(nullable = true, name = "terms")
    private String terms;

    @Column(nullable = true, name = "shipper_number")
    private String shipperNumber;

    @Column(nullable = true, name = "pro_number")
    private String proNumber;

    @Column(nullable = true, name = "ra_number")
    private String raNumber;

    @Column(nullable = true, name = "econtrol_number")
    private String eControlNumber;

    @Column(nullable = true, name = "driver_number")
    private String driverNumber;

    @Column(nullable = true, name = "run_number")
    private String runNumber;

    @Column(nullable = true, name = "cubic_feet")
    private String cubicFeet;

    @Column(nullable = true, name = "time_departed")
    private String timeDeparted;

    @Column(nullable = true, name = "time_arrived")
    private String timeArrived;

    @Column(nullable = true, name = "date")
    private String date;

    @Column(nullable = false, name = "status")
    private int status;

    @Id
    @Column(nullable = false, name = "shipper_tbl_id")
    private int shipperTableID;

    @Id
    @Column(nullable = false, name = "consignee_tbl_id")
    private int consigneeTableID;

    @Id
    @Column(nullable = false, name = "bill_to_tbl_id")
    private int billToTableID;

    @Id
    @Column(nullable = false, name = "instructions_tbl_id")
    private int instructionTableID;

    @Id
    @Column(nullable = false, name = "totals_tbl_id")
    private int totalsTableID;
}
