package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
@Getter
@Setter
public class OrderCreatedDTO {
    private long id;
    private String secretPaymentUrl;
    private Time estimatedTimeOfArrival;
}
