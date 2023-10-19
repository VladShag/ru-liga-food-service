package ru.liga.deliveryservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.liga.deliveryservice.entity.Delivery;

import java.util.List;
@Getter
@Setter
public class DelieveryDTO {
    List<Delivery> deliveries;
    private int pageIndex;
    private int pageCount = 10;

}
