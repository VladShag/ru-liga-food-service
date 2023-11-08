package ru.liga.kitchenservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Позиция в заказе для показа клиенту")
public class ItemToShowCustomerDTO {
    @NotNull
    @Schema(description = "Цена")
    private int price;
    @NotNull
    @Schema(description = "Количство")
    private int quantity;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Ссылка на изображение")
    private String image;
}
