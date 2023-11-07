package ru.liga.kitchenservice.dto;

import lombok.Data;
import ru.liga.common.entity.OrderItem;

import java.util.List;

@Data
public class OrderToKitchenDTO {
    List<OrderItem> itemList;
}
