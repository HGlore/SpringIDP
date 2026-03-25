package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntriesDTO {
    private DocumentDTO documentDTO;
    private ProductionDTO productionDTO;
    private List<Integer> ids;
    private String updateTo;
}
