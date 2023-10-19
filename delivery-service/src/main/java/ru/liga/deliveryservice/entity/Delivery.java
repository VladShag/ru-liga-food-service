package ru.liga.deliveryservice.entity;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Delivery {
    private long orderId;
    private Restaurant restaurant;
    private Customer customer;
    private int payment;


}
