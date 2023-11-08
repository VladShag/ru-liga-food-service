package ru.liga.deliveryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "DTO Списка заказов на доставку")
public class DelieveryListDTO {
    @Schema(description = "Список DTO доставок")
    List<DeliveryDTO> deliveries;
    @Schema(description = "Номер страницы")
    private int pageIndex = 0;
    @Schema(description = "Количество страниц")
    private int pageCount = 10;

}
