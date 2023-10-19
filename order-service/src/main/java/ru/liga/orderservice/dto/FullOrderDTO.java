package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.liga.orderservice.entity.Restaurant;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class FullOrderDTO extends OrderDTOInt {
    private long id;
    private Restaurant restaurant;
    private Date timestamp;
    private List<ItemToShowCustomerDTO> items;
}
