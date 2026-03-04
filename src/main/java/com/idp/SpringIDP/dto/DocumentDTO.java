package com.idp.SpringIDP.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

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

}
