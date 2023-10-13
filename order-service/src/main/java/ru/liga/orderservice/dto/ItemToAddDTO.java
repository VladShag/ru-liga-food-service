package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemToAddDTO {
    private int quantity;
    private long menuItemId;
}
