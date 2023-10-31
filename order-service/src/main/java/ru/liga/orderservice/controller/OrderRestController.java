package ru.liga.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.*;
import ru.liga.common.entity.Status;
import ru.liga.orderservice.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Tag(name = "Заказы", description = "В данном контроллере описаны методы для взаимодействия с заказами")
public class OrderRestController {
    private final OrderService service;

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
    public FullOrderDTO getOrderById(@PathVariable("id")@Parameter(description = "ID заказа") long id) {
        return service.getOrderById(id);
    }

    @GetMapping("/orderByStatus")
    @Operation(
            summary = "Получение зазаков по статусу",
            description = "Метод, выводящий все заказы по конкретному статусу, который задан в качестве параметра. " +
                    "Выводит список в виде DTO"
    )
    public MainOrderListDTO getOrdersByStatus(@RequestParam("status")@Parameter(description = "Статус заказа") Status status) {
        return service.getOrdersByStatus(status);
    }

    @PostMapping("/")
    @Operation(
            summary = "Создать новый заказ",
            description = "Метод, в теле которого передается DTO создания нового заказа и заказ сохраняется в БД. " +
                    "Обратно при удачной работе метода выводит DTO с id заказа, ссылкой на оплату и временем доставки"
    )
    public OrderCreatedDTO addNewOrder(@RequestBody @Parameter(description = "DTO для создания заказа") OrderToCreateDTO dto) {
        return service.addNewOrder(dto);
    }

    @PostMapping("/status/{id}")
    @Operation(
            summary = "Установить статус заказа",
            description = "Метод позволяющий установить статус заказа, новый статус передается в DTO, которая передается в теле запроса. " +
                    "В ответ при удачной отработке метода выводится вся информация о заказе"
    )
    public FullOrderDTO setDeliveryStatus(@PathVariable("id")@Parameter(description = "ID заказа") long id, @RequestBody  @Parameter(description = "DTO для смены статуса") ChangeStatusDTO dto) {
        Status status = dto.getOrderAction();
        service.setOrderStatus(id, status);
        return service.getOrderById(id);
    }

}
