package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "DTO для создания заказа")
public class OrderToCreateDTO {
    @NotNull
    @Min(0)
    @Schema(description = "Идентификатор ресторана")
    private long restaurantId;
    @NotEmpty
    @Schema(description = "Позиции в заказе")
    private List<ItemToAddDTO> menuItems;
}
