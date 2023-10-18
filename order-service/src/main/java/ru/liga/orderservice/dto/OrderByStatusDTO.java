package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class OrderByStatusDTO extends OrderDTOInt {
    private long id;
    private List<ItemToAddDTO> menuItems;
}
