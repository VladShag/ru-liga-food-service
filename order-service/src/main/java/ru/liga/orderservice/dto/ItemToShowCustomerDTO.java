package ru.liga.orderservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemToShowCustomerDTO {
    @NotNull
    private int price;
    @NotNull
    private int quantity;
    private String description;
    private String image;
}
