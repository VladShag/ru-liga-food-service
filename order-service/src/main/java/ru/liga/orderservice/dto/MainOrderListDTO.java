package ru.liga.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class MainOrderListDTO {
        private List<? extends OrderDTOInt> orders;
        private int pageIndex;
        private int pageCount = 10;

}
