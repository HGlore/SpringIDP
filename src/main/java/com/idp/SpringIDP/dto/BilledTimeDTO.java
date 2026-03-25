package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilledTimeDTO {
    private String time;
    private int billed;
}
