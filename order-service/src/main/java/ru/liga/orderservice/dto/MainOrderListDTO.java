package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MainOrderListDTO {
        private List<OrderDTOInt> orders;
        private int pageIndex;
        private int pageCount = 10;

}
