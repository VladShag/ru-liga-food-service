package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemToShowCustomerDTO {
    private int price;
    private int quantity;
    private String description;
    private String image;
}
