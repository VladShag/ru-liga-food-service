package ru.liga.orderservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
@Data
public class OrderCreatedDTO {
    @NotNull
    private long id;
    @NotBlank
    private String secretPaymentUrl;
    @NotNull
    private Time estimatedTimeOfArrival;
}
