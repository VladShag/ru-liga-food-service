package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Позиция в заказе для добавления")
public class ItemToAddDTO {
    @NotNull
    @Schema(description = "Количество")
    private int quantity;
    @NotNull
    @Schema(description = "Идентификатор в меню ресторана")
    private long menuItemId;
}
