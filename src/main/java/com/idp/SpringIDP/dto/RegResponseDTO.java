package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegResponseDTO {
    private String companyID;
    private boolean success;
    private String message;
}
