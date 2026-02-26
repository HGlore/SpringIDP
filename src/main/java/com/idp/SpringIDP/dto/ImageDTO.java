package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDTO {

    public ImageDTO(String storeDate){
        this.storeDate = storeDate;
    }

    private String storeDate;
    private int totalQueue;
    private int newImages;
    private int billedImages;
}
