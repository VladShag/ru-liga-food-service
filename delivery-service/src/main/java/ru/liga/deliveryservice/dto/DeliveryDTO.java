package ru.liga.deliveryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.liga.common.entity.Customer;
import ru.liga.common.entity.Restaurant;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO полной информации о доставке")
public class DeliveryDTO {
    @NotNull
    @Schema(description = "ID заказа")
    private UUID orderId;
    @NotNull
    @Schema(description = "Информация о ресторане")
    private Restaurant restaurant;
    @NotNull
    @Schema(description = "Информация о клиенте")
    private Customer customer;
    @NotNull
    @Schema(description = "Стоимость доставки")
    private int payment;


}
