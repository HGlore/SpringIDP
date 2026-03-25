package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDTO {
    private int documentTableID;
    private String companyID;
    private String productionDate;
    private String startTime;
    private String endTime;
    private String comment;
}
