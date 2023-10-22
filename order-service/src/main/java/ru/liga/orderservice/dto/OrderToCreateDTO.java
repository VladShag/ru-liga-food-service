package ru.liga.orderservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderToCreateDTO {
    @NotNull
    private long restaurantId;
    @NotEmpty
    private List<ItemToAddDTO> menuItems;
}
