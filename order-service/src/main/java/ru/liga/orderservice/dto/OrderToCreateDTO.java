package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.liga.orderservice.entity.Item;

import java.util.List;

@Getter
@Setter
public class OrderToCreateDTO {
    private int restaurant_id;
    private List<ItemToAddDTO> menu_items;
}
