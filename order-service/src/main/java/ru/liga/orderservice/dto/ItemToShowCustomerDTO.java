package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemToShowCustomerDTO {
    private int price;
    private int quantity;
    private String description;
    private String image;
}
