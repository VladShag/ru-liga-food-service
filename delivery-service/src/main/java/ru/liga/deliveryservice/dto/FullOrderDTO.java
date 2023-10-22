package ru.liga.deliveryservice.dto;

import lombok.Data;
import ru.liga.deliveryservice.entity.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class FullOrderDTO {
    @NotNull
    private long id;
    @NotBlank
    private Restaurant restaurant;
    @NotBlank
    private Date timestamp;
    @NotEmpty
    private List<ItemToShowCustomerDTO> items;
}
