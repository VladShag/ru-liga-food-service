package ru.liga.orderservice.dto;

import lombok.Data;
import ru.liga.orderservice.entity.Restaurant;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class FullOrderDTO extends OrderDTOInt {
    @NotNull
    private long id;
    @NotBlank
    private Restaurant restaurant;
    @NotBlank
    private Date timestamp;
    @NotEmpty
    private List<ItemToShowCustomerDTO> items;
}
