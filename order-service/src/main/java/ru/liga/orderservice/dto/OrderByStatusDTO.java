package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderByStatusDTO implements OrderDTOInt {
    private long id;
    private List<ItemToAddDTO> menuItems;
}
