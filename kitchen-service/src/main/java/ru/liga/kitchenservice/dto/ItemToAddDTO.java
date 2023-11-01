package ru.liga.kitchenservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemToAddDTO {
    @NotNull
    private int quantity;
    @NotNull
    private long menuItemId;
}
