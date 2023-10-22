package ru.liga.deliveryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CaseDTO {
    private Long caseId;
    private  String info;
    private LocalDate createDt;
}
