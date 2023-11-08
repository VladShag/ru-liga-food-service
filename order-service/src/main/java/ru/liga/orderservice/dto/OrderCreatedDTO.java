package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.UUID;

@Data
@Schema(description = "DTO созданного заказа")
public class OrderCreatedDTO {
    @NotNull
    @Schema(description = "Идентификатор")
    private UUID id;
    @NotBlank
    @Schema(description = "Ссылка на оплату заказа")
    private String secretPaymentUrl;
    @NotNull
    @Schema(description = "Время доставки")
    private Time estimatedTimeOfArrival;
}
