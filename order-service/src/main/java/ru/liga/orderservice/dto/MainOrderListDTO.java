package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import java.util.List;

@Data
@Schema(description = "Список заказов")
public class MainOrderListDTO {
        @Schema(description = "Заказы")
        private List<? extends OrderDTOInt> orders;
        @Schema(description = "Номер страницы")
        private int pageIndex = 0;
        @Schema(description = "Количество заказов на странице")
        private int pageCount = 10;

}
