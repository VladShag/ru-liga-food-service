package ru.liga.kitchenservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Status;
import ru.liga.kitchenservice.dto.ChangeStatusDTO;
import ru.liga.kitchenservice.dto.OrderToKitchenDTO;
import ru.liga.kitchenservice.service.OrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kitchen")
@Tag(name = "Кухня", description = "В данном контроллере описаны методы для взаимодействия с заказами из кухни")
public class OrderController {
    private final OrderService service;
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить заказ по ID",
            description = "Метод, позволяющий получить заказ на доставку по ID"
    )
    public OrderToKitchenDTO getOrderById(@PathVariable("id") long id) {
        return service.getOrderById(id);
    }


    @PostMapping("/status/{id}")
    @Operation(
            summary = "Установить статус доставки",
            description = "Метод, позволяющий установить статус заказа"
    )
    public void setDeliveryStatus(@PathVariable("id") long id, @RequestBody ChangeStatusDTO dto) {
        Status status = dto.getOrderAction();
        service.setOrderStatus(id, status);
    }
}
