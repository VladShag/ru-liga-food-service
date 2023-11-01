package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.liga.common.entity.Restaurant;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "DTO полной информации о заказе")
public class FullOrderDTO extends OrderDTOInt {
    @NotNull
    @Schema(description = "Идентификатор заказа")
    private long id;
    @NotBlank
    @Schema(description = "Ресторан")
    private Restaurant restaurant;
    @NotBlank
    @Schema(description = "Дата заказа")
    private Date timestamp;
    @NotEmpty
    @Schema(description = "Позиции заказа")
    private List<ItemToShowCustomerDTO> items;
}
