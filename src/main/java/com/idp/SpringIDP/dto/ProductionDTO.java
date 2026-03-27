package com.idp.SpringIDP.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDTO {
    private String productionDate;

    @JsonFormat(pattern = "M/d/yyyy, h:mm:ss a")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "M/d/yyyy, h:mm:ss a")
    private LocalDateTime endTime;
    private String comment;
}
