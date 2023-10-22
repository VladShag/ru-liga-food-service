package ru.liga.deliveryservice.dto;

import lombok.Data;

import java.util.List;
@Data
public class DelieveryListDTO {
    List<DeliveryDTO> deliveries;
    private int pageIndex = 0;
    private int pageCount = 10;

}
