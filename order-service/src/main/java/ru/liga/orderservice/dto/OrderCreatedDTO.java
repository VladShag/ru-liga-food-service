package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
@Getter
@Setter
@ToString
public class OrderCreatedDTO {
    private long id;
    private String secretPaymentUrl;
    private Time estimatedTimeOfArrival;
}
