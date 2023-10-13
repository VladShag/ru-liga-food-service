package ru.liga.orderservice.dto;

import java.util.List;

public class OrderByStatusDTO implements OrderDTOInt {
    private long id;
    private List<ItemToAddDTO> menuItems;
}
