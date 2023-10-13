package ru.liga.orderservice.entity;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private long id;
    private long orderId;
    private long restaurantMenuItem;
    private int price;
    private int quantity;

}
