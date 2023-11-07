package ru.liga.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.OrderItem;
import ru.liga.orderservice.service.OrderItemService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderItem")
@Tag(name = "Позиция заказа", description = "В данном контроллере описаны методы для взаимодействия с позициями заказов")
public class OrderItemRestController {
    private final OrderItemService service;

    @GetMapping("/{id}")
    @Operation(
            summary = "Позиция по ID",
            description = "Метод, позволяющий получить позицию заказа по идентификатору"
    )
    public OrderItem getOrderItemById(@PathVariable("id") @Parameter(description = "ID позиции в заказе") @Min(0) long id) {
        return service.getItemById(id);
    }

    @GetMapping("/order/{id}")
    @Operation(
            summary = "Позиции по ID заказа",
            description = "Метод, позволяющий получить позиции заказа по идентификатору заказа"
    )
    public List<OrderItem> getOrderItemsByOrderId(@PathVariable("id") @Min(0) long orderId) {
        return service.getOrderItemsByOrderId(orderId);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить позицию",
            description = "Метод, позволяющий удалить позицию из заказа"
    )
    public void deleteOrderItemById(@PathVariable("id") @Parameter(description = "ID позиции в заказе") @Min(0) long id) {
        service.deleteItemById(id);
    }
}
