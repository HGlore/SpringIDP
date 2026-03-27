package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ImageDTO {

    public ImageDTO(String storeDate) {
        this.storeDate = storeDate;
    }

    private String storeDate;
    private int unbilledImages;
    private int newImages;
    private int billedImages;
    private List<BilledTimeDTO> billedTimeDTOList;
}
