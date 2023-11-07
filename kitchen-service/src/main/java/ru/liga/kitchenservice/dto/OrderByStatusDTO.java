package ru.liga.kitchenservice.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderByStatusDTO {
    @NotNull
    private long id;
    @NotEmpty
    private List<ItemToAddDTO> menuItems;
}