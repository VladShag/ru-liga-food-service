package ru.liga.orderservice.dto;

import lombok.Data;
import ru.liga.orderservice.entity.Status;


import javax.validation.constraints.NotBlank;

@Data
public class ChangeStatusDTO {
    @NotBlank
    public Status orderAction;
}
