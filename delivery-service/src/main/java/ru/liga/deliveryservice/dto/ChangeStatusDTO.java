package ru.liga.deliveryservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.liga.deliveryservice.entity.Status;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeStatusDTO {
    @NotBlank
    public Status orderAction;
}
