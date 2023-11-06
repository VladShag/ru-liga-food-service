package ru.liga.kitchenservice.dto;

import lombok.Data;
import ru.liga.common.entity.RestaurantStatus;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeRestaurantStatusDTO {
    @NotBlank
    public RestaurantStatus restaurantStatus;
}