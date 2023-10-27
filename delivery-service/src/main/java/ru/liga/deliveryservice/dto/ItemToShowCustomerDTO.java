package ru.liga.deliveryservice.dto;

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
