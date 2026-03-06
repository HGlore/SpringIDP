package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String companyID;
    private String role;
    private String status;
}
