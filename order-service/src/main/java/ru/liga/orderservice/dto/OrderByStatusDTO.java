package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "Заказ по статусу")
public class OrderByStatusDTO extends OrderDTOInt {
    @NotNull
    @Schema(description = "Идентификатор")
    private long id;
    @NotEmpty
    @Schema(description = "Позиции заказа")
    private List<ItemToAddDTO> menuItems;
}
