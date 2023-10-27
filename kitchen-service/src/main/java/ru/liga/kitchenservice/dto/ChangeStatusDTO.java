package ru.liga.kitchenservice.dto;

import lombok.Data;
import ru.liga.common.entity.Status;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeStatusDTO {
    @NotBlank
    public Status orderAction;
}
