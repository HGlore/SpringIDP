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
    public DocumentDTO(Document d,
                       String imageName,
                       Shipper shipper,
                       Consignee consignee,
                       BillTo billTo,
                       Instructions instructions,
                       Totals totals,
                       List<Items> itemsList) {

        this.id = d.getId();
        this.imageName = imageName;
        this.companyID = d.getCompanyID();
        this.startTime = d.getStartTime();
        this.endTime = d.getEndTime();
        this.image = d.getImage();
        this.accountType = d.getAccountType();
        this.detectedAccType = d.getDetectedAccType();
        this.bolNumber = d.getBolNumber();
        this.masterBolNumber = d.getMasterBolNumber();
        this.poNumber = d.getPoNumber();
        this.quoteNumber = d.getQuoteNumber();
        this.terms = d.getTerms();
        this.shipperNumber = d.getShipperNumber();
        this.proNumber = d.getProNumber();
        this.raNumber = d.getRaNumber();
        this.eControlNumber = d.getEControlNumber();
        this.driverNumber = d.getDriverNumber();
        this.runNumber = d.getRunNumber();
        this.cubicFeet = d.getCubicFeet();
        this.timeDeparted = d.getTimeDeparted();
        this.timeArrived = d.getTimeArrived();
        this.date = d.getDate();
        this.status = d.getStatus();
        this.archive = d.getArchive();

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
