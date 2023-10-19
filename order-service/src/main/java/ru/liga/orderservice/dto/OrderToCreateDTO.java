package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderToCreateDTO {
    private long restaurantId;
    private List<ItemToAddDTO> menuItems;
}
