package ru.liga.deliveryservice.dto;

import lombok.*;
import ru.liga.common.entity.Customer;
import ru.liga.common.entity.Restaurant;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    @NotNull
    private UUID orderId;
    @NotNull
    private Restaurant restaurant;
    @NotNull
    private Customer customer;
    @NotNull
    private int payment;


}
