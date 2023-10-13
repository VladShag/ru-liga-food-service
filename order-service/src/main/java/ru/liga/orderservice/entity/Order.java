package ru.liga.orderservice.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private long id;
    private long customerId;
    private long restaurantId;
    private String status;
    private long courierId;
    private Date timestamp;
    private List<Item> items;
}
