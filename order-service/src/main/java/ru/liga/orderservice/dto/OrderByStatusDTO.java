package ru.liga.orderservice.dto;

import lombok.Data;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class OrderByStatusDTO extends OrderDTOInt {
    @NotNull
    private long id;
    @NotEmpty
    private List<ItemToAddDTO> menuItems;
}
