package ru.liga.deliveryservice.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderToDeliveryServiceDTO {
    @NotNull
    @Min(0)
    private long id;
    @NotBlank
    private String restaurantCoordinates;
}
