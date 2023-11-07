package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.liga.common.entity.Status;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "DTO для смены статуса")
public class ChangeStatusDTO {
    @NotNull
    @Schema(description = "Новый статус для установки")
    public String orderAction;
}
