package ru.liga.kitchenservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.liga.common.entity.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "DTO полной информации о заказе")
public class FullOrderDTO {
    @NotNull
    @Schema(description = "Идентификатор заказа")
    private UUID id;
    @NotBlank
    @Schema(description = "Дата заказа")
    private Date timestamp;
    @NotEmpty
    @Schema(description = "Позиции заказа")
    private List<ItemToShowCustomerDTO> items;
}
