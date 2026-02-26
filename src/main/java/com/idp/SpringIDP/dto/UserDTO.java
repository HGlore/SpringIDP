package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class UserDTO {
    private String companyID;
    private String role;
    private String jwtStatus;
}
