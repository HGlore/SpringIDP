package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusDTO {
    private int id;
    private String companyID;
    private int status;
    private int billed;
    private int outputted;
}
