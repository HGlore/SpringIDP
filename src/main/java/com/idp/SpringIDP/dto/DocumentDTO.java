package com.idp.SpringIDP.dto;

import com.idp.SpringIDP.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {
    public DocumentDTO(Document document,
                       String imageName,
                       Shipper shipper,
                       Consignee consignee,
                       BillTo billTo,
                       Instructions instructions,
                       Totals totals,
                       List<Items> itemsList) {

        this.id = document.getId();
        this.imageName = imageName;
        this.companyID = document.getCompanyID();
        this.startTime = document.getStartTime();
        this.endTime = document.getEndTime();
        this.image = document.getImage();
        this.accountType = document.getAccountType();
        this.detectedAccType = document.getDetectedAccType();
        this.bolNumber = document.getBolNumber();
        this.masterBolNumber = document.getMasterBolNumber();
        this.poNumber = document.getPoNumber();
        this.quoteNumber = document.getQuoteNumber();
        this.terms = document.getTerms();
        this.shipperNumber = document.getShipperNumber();
        this.proNumber = document.getProNumber();
        this.raNumber = document.getRaNumber();
        this.eControlNumber = document.getEControlNumber();
        this.driverNumber = document.getDriverNumber();
        this.runNumber = document.getRunNumber();
        this.cubicFeet = document.getCubicFeet();
        this.timeDeparted = document.getTimeDeparted();
        this.timeArrived = document.getTimeArrived();
        this.date = document.getDate();
        this.status = document.getStatus();
        this.archive = document.getArchive();

        // Objects
        this.shipper = shipper;
        this.consignee = consignee;
        this.billTo = billTo;
        this.instructions = instructions;
        this.totals = totals;
        this.items = itemsList;
    }

    private int id;
    private String imageName;
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
