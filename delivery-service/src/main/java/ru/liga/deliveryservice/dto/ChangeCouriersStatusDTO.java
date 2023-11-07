package ru.liga.deliveryservice.dto;

import lombok.Data;
import ru.liga.common.entity.CourierStatus;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeCouriersStatusDTO {
    @NotBlank
    public CourierStatus courierStatus;
}
