package com.idp.SpringIDP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OngoingEntriesDTO {
    private boolean isOngoing;
    private String storedDate;
}
