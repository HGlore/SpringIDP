package com.idp.SpringIDP.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntriesBatchDTO {
    private List<Integer> ids;
    private String date;
}
