package ru.liga.kitchenservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Status;
import ru.liga.kitchenservice.dto.FullOrderDTO;
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
            description = "Метод, позволяющий получить заказ на кухню по ID"
    )
    public FullOrderDTO acceptOrder(@PathVariable("id") UUID id) {
        return service.setOrderStatus(id, Status.KITCHEN_ACCEPTED.toString());
    }
    @PostMapping("/{id}/decline")
    @Operation(
            summary = "Отклонить заказ по ID",
            description = "Метод, позволяющий отклонить входящий заказ по ID"
    )
    public FullOrderDTO declineOrder(@PathVariable("id") UUID id) {
        return service.setOrderStatus(id, Status.KITCHEN_DENIED.toString());
    }
    @PostMapping("/{id}/ready")
    @Operation(
            summary = "Отметить заказ готовым по ID",
            description = "Метод, позволяющий поставить статус \"Готов\" на заказ по ID"
    )
    public FullOrderDTO endOrder(@PathVariable("id") UUID id) {
        return service.setOrderStatus(id, Status.DELIVERY_PENDING.toString());
    }
}
