package ru.liga.orderservice.dto;

import lombok.Data;


import java.util.List;

@Data
public class MainOrderListDTO {
        private List<? extends OrderDTOInt> orders;
        private int pageIndex = 0;
        private int pageCount = 10;

}
