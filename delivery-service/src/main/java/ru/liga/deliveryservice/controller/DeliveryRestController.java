package ru.liga.deliveryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Status;
import ru.liga.deliveryservice.dto.DelieveryListDTO;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
@Tag(name = "Доставки", description = "В данном контроллере описаны методы для взаимодействия с заказами на доставке")
public class DeliveryRestController {
    private final DeliveryService service;
    @GetMapping("/")
    @Operation(
            summary = "Получить заказы по cтатусу",
            description = "Метод, позволяющий получить список заказов на доставку по статусу. Статус передается в параметре status"
    )
    public DelieveryListDTO getDeliveriesByStatus(@RequestParam("status") String status) {
        return service.getDeliveriesByStatus(status);
    }

    @PostMapping("/{id}/take")
    @PreAuthorize("hasAuthority('ROLE_COURIER')")
    @Operation(
            summary = "Принять заказ",
            description = "Метод, позволяющий принять заказ на доставку по ID"
    )
    public DeliveryDTO acceptDelivery(@PathVariable("id") UUID id) {
        return service.setDeliveryStatus(id, Status.DELIVERY_PICKING.toString());
    }
    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAuthority('ROLE_COURIER')")
    @Operation(
            summary = "Завершить заказ",
            description = "Метод, позволяющий принять заказ на доставку по ID"
    )
    public DeliveryDTO completeDelivery(@PathVariable("id") UUID id) {
        return service.setDeliveryStatus(id, Status.DELIVERY_COMPLETE.toString());
    }
}
