package com.idp.SpringIDP.dto;

import com.idp.SpringIDP.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocumentDTO {
    private int id;
    private String companyID;
    private String startTime;
    private String endTime;
    private String image;
    private String accountType;
    private String detectedAccType;
    private String bolNumber;
    private String masterBolNumber;
    private String poNumber;
    private String quoteNumber;
    private String terms;
    private String shipperNumber;
    private String proNumber;
    private String raNumber;
    private String eControlNumber;
    private String driverNumber;
    private String runNumber;
    private String cubicFeet;
    private String timeDeparted;
    private String timeArrived;
    private String date;
    private int status;
    private int archive;
    private Shipper shipper;
    private Consignee consignee;
    private BillTo billTo;
    private Instructions instructions;
    private Totals totals;
    private List<Items> items;
}
