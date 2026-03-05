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

    @Column(nullable = false, name = "stored_images_tbl_id")
    private int storedImageTableID;

    @Column(name = "company_id")
    private String companyID;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    private String image;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "detected_acc_type")
    private String detectedAccType;

    @Column(name = "bol_number")
    private String bolNumber;

    @Column(name = "master_bol_number")
    private String masterBolNumber;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "quote_number")
    private String quoteNumber;

    private String terms;

    @Column(name = "shipper_number")
    private String shipperNumber;

    @Column(name = "pro_number")
    private String proNumber;

    @Column(name = "ra_number")
    private String raNumber;

    @Column(name = "econtrol_number")
    private String eControlNumber;

    @Column(name = "driver_number")
    private String driverNumber;

    @Column(name = "run_number")
    private String runNumber;

    @Column(name = "cubic_feet")
    private String cubicFeet;

    @Column(name = "time_departed")
    private String timeDeparted;

    @Column(name = "time_arrived")
    private String timeArrived;

    private String date;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false, name = "shipper_tbl_id")
    private int shipperTableID;

    @Column(nullable = false, name = "consignee_tbl_id")
    private int consigneeTableID;

    @Column(nullable = false, name = "bill_to_tbl_id")
    private int billToTableID;

    @Column(nullable = false, name = "instructions_tbl_id")
    private int instructionTableID;

    @Column(nullable = false, name = "totals_tbl_id")
    private int totalsTableID;

    @Column(nullable = false)
    private int archive;
}
