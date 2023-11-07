package ru.liga.kitchenservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Status;
import ru.liga.kitchenservice.dto.OrderToKitchenDTO;
import ru.liga.kitchenservice.service.OrderService;

import javax.validation.constraints.Min;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kitchen")
@Tag(name = "Кухня", description = "В данном контроллере описаны методы для взаимодействия с заказами из кухни")
public class OrderController {
    private final OrderService service;

    @PostMapping("/{id}/accept")
    @Operation(
            summary = "Принять заказ по ID",
            description = "Метод, позволяющий получить заказ на доставку по ID"
    )
    public OrderToKitchenDTO acceptOrder(@PathVariable("id") @Min(0) UUID id) {
        return service.setOrderStatus(id, Status.KITCHEN_ACCEPTED.toString());
    }
    @PostMapping("/{id}/decline")
    @Operation(
            summary = "Отклонить заказ по ID",
            description = "Метод, позволяющий получить заказ на доставку по ID"
    )
    public OrderToKitchenDTO declineOrder(@PathVariable("id") @Min(0) UUID id) {
        return service.setOrderStatus(id, Status.KITCHEN_DENIED.toString());
    }
    @PostMapping("/{id}/ready")
    @Operation(
            summary = "Отклонить заказ по ID",
            description = "Метод, позволяющий получить заказ на доставку по ID"
    )
    public OrderToKitchenDTO endOrder(@PathVariable("id") @Min(0) UUID id) {
        return service.setOrderStatus(id, Status.DELIVERY_PENDING.toString());
    }
}
