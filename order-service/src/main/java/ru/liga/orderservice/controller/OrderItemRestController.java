package ru.liga.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.entity.OrderItem;
import ru.liga.orderservice.service.OrderItemService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Позиция заказа", description = "В данном контроллере описаны методы для взаимодействия с позициями заказов")
public class OrderItemRestController {
    private final OrderItemService service;

    @GetMapping("/orderItem/{id}")
    @Operation(
            summary = "Позиция по ID",
            description = "Метод, позволяющий получить позицию заказа по идентификатору"
    )
    public OrderItem getOrderItemById(@PathVariable("id")@Parameter(description = "ID позиции в заказе") long id) {
        return service.getItemById(id);
    }

    @DeleteMapping("/orderItem/{id}")
    @Operation(
            summary = "Удалить позицию",
            description = "Метод, позволяющий удалить позицию из заказа"
    )
    public void deleteOrderItemById(@PathVariable("id")@Parameter(description = "ID позиции в заказе") long id) {
        service.deleteItemById(id);
    }
}
