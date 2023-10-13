package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.liga.orderservice.entity.Restaurant;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FullOrderDTO implements OrderDTOInt {
    private long id;
    private Restaurant restaurant;
    private Date timestamp;
    private List<ItemToShowCustomerDTO> items;
}
