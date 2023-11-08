package ru.liga.deliveryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "DTO информации о доставке")
public class OrderToDeliveryServiceDTO {
    @NotNull
    @Min(0)
    @Schema(description = "ID заказа")
    private long id;
    @NotBlank
    @Schema(description = "Координаты ресторана")
    private String restaurantCoordinates;
}
