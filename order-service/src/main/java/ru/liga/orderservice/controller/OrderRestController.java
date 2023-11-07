package ru.liga.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.exceptions.WrongStatusException;
import ru.liga.orderservice.dto.*;
import ru.liga.orderservice.service.OrderService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Заказы", description = "В данном контроллере описаны методы для взаимодействия с заказами")
public class OrderRestController {
    private final OrderService service;
    @PostMapping("/")
    @Operation(
            summary = "Создать новый заказ",
            description = "Метод, в теле которого передается DTO создания нового заказа и заказ сохраняется в БД. " +
                    "Обратно при удачной работе метода выводит DTO с id заказа, ссылкой на оплату и временем доставки"
    )
    public OrderCreatedDTO addNewOrder(@RequestBody @Parameter(description = "DTO для создания заказа") @Valid OrderToCreateDTO dto) {
        return service.addNewOrder(dto);
    }

    @GetMapping("/")
    @Operation(
            summary = "Список всех заказов",
            description = "Метод, позволяющий получить все заказы, которые есть в базе данных" +
                    "информация выводится с помощью ДТО списка заказов со всей информацией"
    )
    public MainOrderListDTO getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение заказа по id",
            description = "Метод позволяющий получить конкретный заказ по его id." +
                    "Выводит информацию в виде DTO полной информации о заказе"
    )
    public FullOrderDTO getOrderById(@PathVariable("id") @Parameter(description = "ID заказа") @Min(0) UUID id) {
        return service.getOrderById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Установить статус заказа",
            description = "Метод позволяющий установить статус заказа, новый статус передается в DTO, которая передается в теле запроса. " +
                    "В ответ при удачной отработке метода выводится вся информация о заказе"
    )
    public FullOrderDTO setOrderStatus(@PathVariable("id") @Parameter(description = "ID заказа") @Min(0) UUID id, @RequestBody @Parameter(description = "DTO для смены статуса") @Valid ChangeStatusDTO dto) {
        List<String> availableStatus = Arrays.asList("CUSTOMER_CREATED", "CUSTOMER_PAID", "CUSTOMER_CANCELED");
        String status = dto.getOrderAction();
        if(!availableStatus.contains(status)) {
            throw new WrongStatusException("You can't set status: " + status + "!");
        }
        return service.setOrderStatus(id, status);
    }

}
