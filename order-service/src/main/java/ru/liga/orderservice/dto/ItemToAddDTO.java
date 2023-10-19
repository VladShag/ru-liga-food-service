package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemToAddDTO {
    private int quantity;
    private long menuItemId;
}
